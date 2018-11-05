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

import static org.junit.Assert.assertEquals;

import com.cognifide.aemrules.htl.checks.AbstractHtlCheck;
import com.cognifide.aemrules.htl.lex.HtlLexer;
import com.google.common.base.Throwables;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.internal.google.common.io.Files;
import org.sonar.plugins.html.api.HtmlConstants;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class HtlScannerTest {

    private HtlNodeCounterVisitor counterVisitor;

    private HtlScanner htlScanner;

    private HtlLexer lexer;

    @Before
    public void setUp() {
        lexer = new HtlLexer();
        htlScanner = new HtlScanner();
        counterVisitor = new HtlNodeCounterVisitor();
        htlScanner.addVisitor(counterVisitor);
    }

    @Test
    public void checkVisitedNode() {
        HtmlSourceCode htmlSourceCode = createHtmlSourceCode("scanner/testFile.html");
        try (Reader reader = readFile("scanner/testFile.html")) {
            List<Node> nodes = lexer.parse(reader);
            htlScanner.scan(nodes, htmlSourceCode);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
        assertEquals(1, counterVisitor.getCounter(CommentNode.class).intValue());
        assertEquals(7, counterVisitor.getCounter(TagNode.class).intValue());
        assertEquals(3, counterVisitor.getCounter(Expression.class).intValue());
    }

    private HtmlSourceCode createHtmlSourceCode(String relativePath) {
        return new HtmlSourceCode(new TestInputFileBuilder("key", relativePath)
            .setLanguage(HtmlConstants.LANGUAGE_KEY)
            .setModuleBaseDir(new File(".").toPath())
            .build()
        );
    }

    private Reader readFile(String fileName) {
        File root = new File("src/test/resources");
        File file = new File(root, fileName);
        try {
            return new StringReader(Files.toString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read " + fileName, e);
        }
    }
}