package com.cognifide.aemrules.htl.visitors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.node.DirectiveNode;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class HtlNodeCounterVisitor implements DefaultHtlVisitor {

    private HtmlSourceCode sourceCode = null;

    private Map<Class, Integer> counterMap;

    public HtlNodeCounterVisitor() {
        counterMap = new HashMap<>();
    }

    @Override
    public HtmlSourceCode getHtmlSourceCode() {
        return sourceCode;
    }

    @Override
    public void setSourceCode(HtmlSourceCode sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Override
    public void characters(TextNode node) {
        incrementCounter(node);
    }

    @Override
    public void comment(CommentNode node) {
        incrementCounter(node);
    }

    @Override
    public void htlComment(CommentNode node) {
        incrementCounter(node);
    }

    @Override
    public void directive(DirectiveNode node) {
        incrementCounter(node);
    }

    @Override
    public void endElement(TagNode node) {
        incrementCounter(node);
    }

    @Override
    public void expression(ExpressionNode node) {
        incrementCounter(node);
    }

    @Override
    public void startElement(TagNode node) {
        incrementCounter(node);
    }

    @Override
    public void htlExpression(Expression expression, Node node) {
        incrementCounter(expression);
    }

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        incrementCounter(node);
    }

    public Integer getCounter(Class nodeClass) {
        return counterMap.getOrDefault(nodeClass, 0);
    }

    private void incrementCounter(Object nodeObject) {
        Class nodeClass = nodeObject.getClass();
        Integer nodeCounter = getCounter(nodeClass);
        counterMap.put(nodeClass, ++nodeCounter);
    }
}
