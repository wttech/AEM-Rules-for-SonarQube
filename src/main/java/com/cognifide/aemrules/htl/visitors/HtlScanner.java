/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2018 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.htl.visitors;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.ExpressionParser;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.Fragment;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.Interpolation;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.node.DirectiveNode;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class HtlScanner {

    private static final ExpressionParser expressionParser = new ExpressionParser();

    private final List<DefaultHtlVisitor> checkVisitors = Lists.newArrayList();
    private static void scanElementTag(DefaultHtlVisitor visitor, TagNode node) {
        if (!node.isEndElement()) {
            visitor.startElement(node);
            if (isHtlTag(node)) {
                List<Expression> expressions =
                    node.getAttributes().stream()
                        .map(Attribute::getValue)
                        .filter(HtlScanner::hasHtlExpression)
                        .flatMap(HtlScanner::getExpressions)
                        .collect(Collectors.toList());
                visitor.startHtlElement(expressions, node);
            }
        }
        if (node.isEndElement() || node.hasEnd()) {
            visitor.endElement(node);
        }
    }

    private static boolean isHtlTag(TagNode node) {
        boolean hasDataSlyAttribute = node.getAttributes().stream()
            .map(Attribute::getName)
            .anyMatch(Syntax::isPluginAttribute);
        boolean isSlyTag = "sly".equalsIgnoreCase(node.getNodeName());
        return isSlyTag || hasDataSlyAttribute;
    }

    private static Stream<Expression> getExpressions(String code) {
        Interpolation interpolation = expressionParser.parseInterpolation(code);
        return StreamSupport
            .stream(interpolation.getFragments().spliterator(), false)
            .filter(Fragment::isExpression)
            .map(Fragment::getExpression);
    }

    private static boolean hasHtlExpression(String code) {
        return getExpressions(code)
            .findAny()
            .isPresent();
    }

    /**
     * Add a visitor to the list of visitors.
     */
    public void addVisitor(DefaultHtlVisitor visitor) {
        checkVisitors.add(visitor);
        visitor.init();
    }

    /**
     * Scan a list of Nodes and send events to the visitors.
     */
    public void scan(List<Node> nodeList, HtmlSourceCode htmlSourceCode) {
        scan(nodeList, htmlSourceCode, checkVisitors);
    }

    private void scan(List<Node> nodeList, HtmlSourceCode htmlSourceCode,
        List<DefaultHtlVisitor> visitors) {
        // prepare the visitors
        for (DefaultHtlVisitor visitor : visitors) {
            visitor.setSourceCode(htmlSourceCode);
        }

        // notify visitors for a new document
        for (DefaultHtlVisitor visitor : visitors) {
            visitor.startDocument(nodeList);
        }

        // notify the visitors for start and end of element
        for (Node node : nodeList) {
            for (DefaultHtlVisitor visitor : visitors) {
                scanElement(visitor, node);
            }
        }

        // notify visitors for end of document
        for (DefaultHtlVisitor visitor : visitors) {
            visitor.endDocument();
        }
    }

    /**
     * Scan a single element and send appropriate event: start element, end element, characters, comment, expression or directive.
     */
    private void scanElement(DefaultHtlVisitor visitor, Node node) {
        switch (node.getNodeType()) {
            case TAG:
                scanElementTag(visitor, (TagNode) node);
                break;
            case TEXT:
                visitor.characters((TextNode) node);
                break;
            case COMMENT:
                visitor.comment((CommentNode) node);
                if (Syntax.isSightlyComment(node.getCode())) {
                    visitor.htlComment((CommentNode) node);
                }
                break;
            case EXPRESSION:
                visitor.expression((ExpressionNode) node);
                break;
            case DIRECTIVE:
                visitor.directive((DirectiveNode) node);
                break;
            default:
                break;
        }

        if (hasHtlExpression(node.getCode())) {
            getExpressions(node.getCode())
                .forEach(expression -> visitor.htlExpression(expression, node));
        }
    }

}
