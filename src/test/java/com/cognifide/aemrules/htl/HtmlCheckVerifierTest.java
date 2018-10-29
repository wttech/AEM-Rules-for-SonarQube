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
    public void checkMultipleIssuesInTheSameLine() {
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