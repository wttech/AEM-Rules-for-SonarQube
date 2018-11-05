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
package com.cognifide.aemrules.htl.checks;


import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.plugins.html.checks.HtmlIssue;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class AbstractHtlCheckTest {

    private AbstractHtlCheck check;

    private HtmlSourceCode htmlSourceCode;

    private static HtmlSourceCode createHtmlSourceCode(String relativePath) {
        return new HtmlSourceCode(new TestInputFileBuilder("key", relativePath).setModuleBaseDir(new File(".").toPath()).build());
    }

    @Before
    public void setUp() {
        this.check = new AbstractHtlCheck();
        this.htmlSourceCode = createHtmlSourceCode("/");
        check.setSourceCode(htmlSourceCode);
    }

    @Test
    public void creatingViolationWithoutCost() {
        check.createViolation(0, "Issue 0");
        List<HtmlIssue> issues = check.getHtmlSourceCode().getIssues();

        assertThat(issues).hasSize(1);
        assertNull(issues.get(0).cost());
    }

    @Test
    public void creatingViolationWithCost() {
        check.createViolation(1, "Issue 0", 1d);
        List<HtmlIssue> issues = check.getHtmlSourceCode().getIssues();

        assertThat(issues).hasSize(1);
        assertEquals(1d, issues.get(0).cost(), 0d);
    }

    @Test
    public void creatingViolations() {
        check.createViolation(0, "Issue 0", 1d);
        check.createViolation(1, "Issue 1");
        List<HtmlIssue> issues = check.getHtmlSourceCode().getIssues();

        assertThat(issues).hasSize(2);
        assertEquals(1d, issues.get(0).cost(), 0d);
        assertNull(issues.get(1).cost());
    }


}