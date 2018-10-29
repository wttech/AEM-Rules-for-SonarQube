/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
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

public class DefaultHtlVisitor {

    private HtmlSourceCode htmlSourceCode;

    public void init() {
    }

    public void characters(TextNode textNode) {
    }

    public void comment(CommentNode node) {
    }

    public void htlComment(CommentNode node) {
    }

    public void directive(DirectiveNode node) {
    }

    public void endDocument() {
    }

    public void endElement(TagNode node) {
    }

    public void expression(ExpressionNode node) {
    }

    public void htlExpression(Expression expression, Node node) {
    }

    public HtmlSourceCode getHtmlSourceCode() {
        return htmlSourceCode;
    }

    public void setSourceCode(HtmlSourceCode sourceCode) {
        this.htmlSourceCode = sourceCode;
    }

    public void startDocument(List<Node> nodes) {
    }

    public void startElement(TagNode node) {
    }

    public void startHtlElement(List<Expression> expressions, TagNode node) {
    }

}
