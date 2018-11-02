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

import java.util.Map;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.sonar.plugins.html.checks.HtmlIssue;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public final class MetricsSaver {

    private MetricsSaver() {
        // private constructor to hide public one
    }

    public static void saveIssues(SensorContext context, HtmlSourceCode sourceCode) {
        InputFile inputFile = sourceCode.inputFile();

        for (HtmlIssue issue : sourceCode.getIssues()) {
            NewIssue newIssue = context.newIssue()
                .forRule(issue.ruleKey())
                .gap(issue.cost());
            Integer line = issue.line();
            NewIssueLocation location = newIssue.newLocation()
                .on(inputFile)
                .message(issue.message());
            if (line != null && line > 0) {
                location.at(inputFile.selectLine(line));
            }
            newIssue.at(location);
            newIssue.save();
        }
    }

    public static void saveMeasures(SensorContext context, HtmlSourceCode sourceCode) {
        InputFile inputFile = sourceCode.inputFile();
        for (Map.Entry<Metric<Integer>, Integer> entry : sourceCode.getMeasures().entrySet()) {
            context.<Integer>newMeasure()
                .on(inputFile)
                .forMetric(entry.getKey())
                .withValue(entry.getValue())
                .save();
        }
    }

    public static void saveLineLevelMeasures(InputFile inputFile, HtmlSourceCode htmlSourceCode, FileLinesContextFactory fileLinesContextFactory) {
        FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(inputFile);
        final int lineContainsCode = 1;

        for (Integer line : htmlSourceCode.getDetailedLinesOfCode()) {
            fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, lineContainsCode);
        }

        fileLinesContext.save();
    }

}
