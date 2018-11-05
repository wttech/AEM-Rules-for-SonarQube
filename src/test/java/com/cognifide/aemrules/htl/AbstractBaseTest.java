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
package com.cognifide.aemrules.htl;

import com.cognifide.aemrules.htl.api.HtlCheck;
import com.cognifide.aemrules.htl.checks.AbstractHtlCheck;
import com.cognifide.aemrules.htl.lex.HtlLexer;
import com.cognifide.aemrules.htl.rules.HtlCheckClasses;
import com.cognifide.aemrules.htl.visitors.HtlScanner;
import com.google.common.base.Throwables;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import org.sonar.plugins.html.api.HtmlConstants;
import org.sonar.plugins.html.checks.HtmlIssue;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public abstract class AbstractBaseTest {

    protected AbstractHtlCheck check;

    protected String filename;

    private static HtmlSourceCode createSourceCode(File file) {
        return new HtmlSourceCode(
            new TestInputFileBuilder("key", file.getPath())
                .setLanguage(HtmlConstants.LANGUAGE_KEY)
                .setType(InputFile.Type.MAIN)
                .setModuleBaseDir(new File(".").toPath())
                .setCharset(StandardCharsets.UTF_8)
                .build()
        );
    }

    private static HtlScanner setupScanner(AbstractHtlCheck check, HtmlCheckVerifier htmlCheckVerifier) {
        HtlScanner scanner = new HtlScanner();
        scanner.addVisitor(new ExpectedIssueCollector(htmlCheckVerifier));
        if (check != null) {
            Class<? extends HtlCheck> htlCheck = check.getClass();
            Rule rule = HtlCheckClasses.getRule(htlCheck);
            RuleKey ruleKey = RuleKey.of(HtlCheckClasses.REPOSITORY_KEY, rule.key());
            check.setRuleKey(ruleKey);
        }
        scanner.addVisitor(check);
        return scanner;
    }

    protected final List<HtmlIssue> verify() {
        File file = new File(filename);
        HtmlSourceCode result = createSourceCode(file);
        HtmlCheckVerifier htmlCheckVerifier = new HtmlCheckVerifier();
        HtlScanner scanner = setupScanner(check, htmlCheckVerifier);

        try (FileReader fileReader = new FileReader(file)) {
            scanner.scan(new HtlLexer().parse(fileReader), result);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }

        htmlCheckVerifier.checkIssues(result.getIssues());
        return result.getIssues();
    }

    private static class ExpectedIssueCollector extends AbstractHtlCheck {

        private final HtmlCheckVerifier verifier;

        ExpectedIssueCollector(HtmlCheckVerifier verifier) {
            this.verifier = verifier;
        }

        @Override
        public void comment(CommentNode node) {
            verifier.collectExpectedIssues(node.getCode(), node.getStartLinePosition());
            super.comment(node);
        }
    }

}
