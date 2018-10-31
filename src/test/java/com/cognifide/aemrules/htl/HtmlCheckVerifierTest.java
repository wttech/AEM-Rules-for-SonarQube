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


import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.html.checks.HtmlIssue;

public class HtmlCheckVerifierTest {

    private HtmlCheckVerifier htmlCheckVerifier = new HtmlCheckVerifier();

    @Before
    public void setUp() {
        htmlCheckVerifier.collectExpectedIssues(HtmlCheckVerifier.ISSUE_MARKER, 1);
    }

    @Test(expected = AssertionError.class)
    public void checkUnexpectedError() {
        List<HtmlIssue> htmlIssues = new ArrayList<>();
        htmlIssues.add(createLineIssue(1));
        htmlIssues.add(createLineIssue(2));
        htmlCheckVerifier.checkIssues(htmlIssues);
    }

    @Test()
    public void checkMultipleExpectedIssuesInTheSameLine() {
        List<HtmlIssue> htmlIssues = new ArrayList<>();
        htmlIssues.add(createLineIssue(1));
        htmlIssues.add(createLineIssue(1));
        htmlCheckVerifier.checkIssues(htmlIssues);
    }

    @Test(expected = AssertionError.class)
    public void checkExpectedIssueButNotReported() {
        List<HtmlIssue> htmlIssues = new ArrayList<>();
        htmlCheckVerifier.checkIssues(htmlIssues);
    }

    private HtmlIssue createLineIssue(Integer line) {
        RuleKey rule = RuleKey.of("r", "r");
        return new HtmlIssue(rule, line, "");
    }
}