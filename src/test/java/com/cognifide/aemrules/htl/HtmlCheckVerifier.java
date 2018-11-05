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


import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.sonar.plugins.html.checks.HtmlIssue;

public class HtmlCheckVerifier {

    public static final String ISSUE_MARKER = "<!--/* Non-Compliant */-->";
    private Set<Integer> expectedIssuesLines;

    public HtmlCheckVerifier() {
        this.expectedIssuesLines = new HashSet<>();
    }

    public void collectExpectedIssues(String comment, int startLine) {
        boolean isIssueMarker = StringUtils.equals(ISSUE_MARKER, comment);
        if (isIssueMarker) {
            expectedIssuesLines.add(startLine);
        }
    }

    public void checkIssues(List<HtmlIssue> issues) {
        Set<Integer> issuesLines = issues.stream()
            .map(HtmlIssue::line)
            .collect(Collectors.toSet());

        for (HtmlIssue issue : issues) {
            assertThat("No issues expected in line: " + issue.line() + ", but got: " + issue.ruleKey(), expectedIssuesLines, hasItem(issue.line()));
        }
        expectedIssuesLines.removeAll(issuesLines);
        issuesLines.clear();

        for (Integer expectedIssueLine : expectedIssuesLines) {
            assertThat("Non-Compliant declared, but no issues present at line: " + expectedIssueLine, issuesLines, hasItem(expectedIssueLine));
        }
    }
}
