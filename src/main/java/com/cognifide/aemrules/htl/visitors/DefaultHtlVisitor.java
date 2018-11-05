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

import java.util.List;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.node.DirectiveNode;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public interface DefaultHtlVisitor {

    default void init() {
    }

    default void characters(TextNode textNode) {
    }

    default void comment(CommentNode node) {
    }

    default void htlComment(CommentNode node) {
    }

    default void directive(DirectiveNode node) {
    }

    default void endDocument() {
    }

    default void endElement(TagNode node) {
    }

    default void expression(ExpressionNode node) {
    }

    default void htlExpression(Expression expression, Node node) {
    }

    HtmlSourceCode getHtmlSourceCode();

    void setSourceCode(HtmlSourceCode sourceCode);

    default void startDocument(List<Node> nodes) {
    }

    default void startElement(TagNode node) {
    }

    default void startHtlElement(List<Expression> expressions, TagNode node) {
    }

}
