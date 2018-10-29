package com.cognifide.aemrules.htl.visitors;

import static org.junit.Assert.assertEquals;

import com.cognifide.aemrules.htl.checks.AbstractHtlCheck;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.plugins.html.api.HtmlConstants;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.node.DirectiveNode;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class HtlScannerTest {

    private CounterVisitor counterVisitor;

    private HtlScanner htlScanner;

    @Before
    public void setUp() {
        htlScanner = new HtlScanner();
        this.counterVisitor = new CounterVisitor();
        htlScanner.addVisitor(counterVisitor);
    }

    @Test
    public void checkVisitedNode() {
        List<Node> nodes = new ArrayList<>();
        HtmlSourceCode htmlSourceCode = createHtmlSourceCode("test/testFile.html");
        htlScanner.scan(nodes, htmlSourceCode);
        assertEquals(3, counterVisitor.getCounter());
    }

    private HtmlSourceCode createHtmlSourceCode(String relativePath) {
        return new HtmlSourceCode(new TestInputFileBuilder("key", relativePath)
            .setLanguage(HtmlConstants.LANGUAGE_KEY)
            .setModuleBaseDir(new File(".").toPath())
            .build()
        );
    }

    private class CounterVisitor extends AbstractHtlCheck {

        private int counter = 0;

        @Override
        public void init() {
            counter++;
        }

        @Override
        public void characters(TextNode textNode) {
            counter++;
        }

        @Override
        public void comment(CommentNode node) {
            counter++;
        }

        @Override
        public void htlComment(CommentNode node) {
            counter++;
        }

        @Override
        public void directive(DirectiveNode node) {
            counter++;
        }

        @Override
        public void endDocument() {
            counter++;
        }

        @Override
        public void endElement(TagNode node) {
            counter++;
        }

        @Override
        public void expression(ExpressionNode node) {
            counter++;
        }

        @Override
        public void htlExpression(Expression expression, Node node) {
            counter++;
        }

        @Override
        public void startDocument(List<Node> nodes) {
            counter++;
        }

        @Override
        public void startElement(TagNode node) {
            counter++;
        }

        @Override
        public void startHtlElement(List<Expression> expressions, TagNode node) {
            counter++;
        }

        public int getCounter() {
            return counter;
        }
    }
}