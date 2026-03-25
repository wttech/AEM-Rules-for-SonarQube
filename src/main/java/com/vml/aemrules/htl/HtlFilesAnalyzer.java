/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2024 VML
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
package com.vml.aemrules.htl;

import com.vml.aemrules.htl.checks.ParsingErrorCheck;
import com.vml.aemrules.htl.rules.HtlRulesList;
import com.vml.aemrules.utils.Throwables;
import org.apache.sling.scripting.sightly.compiler.SightlyCompilerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonarsource.analyzer.commons.ProgressReport;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.CancellationException;

import static com.vml.aemrules.htl.Constants.REPOSITORY_KEY;


public abstract class HtlFilesAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtlFilesAnalyzer.class);
    protected final HtlChecks checks;
    private final RuleKey parsingErrorRuleKey;

    protected HtlFilesAnalyzer(CheckFactory checkFactory) {
        this.checks = HtlChecks.createHtlCheck(checkFactory)
                .addChecks(REPOSITORY_KEY, HtlRulesList.getChecks());
        this.parsingErrorRuleKey = setupParsingErrorRuleKey(checks);
    }

    private static RuleKey setupParsingErrorRuleKey(HtlChecks checks) {
        return checks.getAll().stream()
                .filter(check -> check.getClass() == ParsingErrorCheck.class)
                .findFirst()
                .map(checks::ruleKeyFor)
                .orElse(null);
    }

    private static void stopProgressReport(ProgressReport progressReport, boolean success) {
        if (success) {
            progressReport.stop();
        } else {
            progressReport.cancel();
        }
    }

    private static void checkInterrupted(Exception e) {
        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof InterruptedException || cause instanceof InterruptedIOException) {
            throw new AnalysisException("Analysis cancelled", e);
        }
    }

    private static void processException(Exception e, SensorContext sensorContext, InputFile inputFile) {
        sensorContext.newAnalysisError()
                .onFile(inputFile)
                .message(e.getMessage())
                .save();
    }

    public void analyseFiles(SensorContext context, Iterable<InputFile> inputFiles, ProgressReport progressReport) {
        boolean success = false;
        try {
            for (InputFile inputFile : inputFiles) {
                if (context.isCancelled()) {
                    throw new CancellationException(
                            "Analysis interrupted because the SensorContext is in cancelled state");
                }
                analyse(context, inputFile);
                progressReport.nextFile();
            }
            success = true;
        } catch (CancellationException e) {
            // do not propagate the exception
            LOGGER.debug(e.toString());
        } finally {
            stopProgressReport(progressReport, success);
        }
    }

    abstract void scanFile(SensorContext sensorContext, InputFile inputFile) throws IOException;

    private void analyse(SensorContext sensorContext, InputFile inputFile) {
        try {
            scanFile(sensorContext, inputFile);
        } catch (SightlyCompilerException e) {
            checkInterrupted(e);
            // Expected for HTL-0 (ParsingErrorCheck) fixtures; avoid ERROR-level noise in scanner logs.
            LOGGER.warn("HTL parse error in {}: {}", inputFile.uri(), e.getMessage());
            processRecognitionException(e, sensorContext, inputFile);
        } catch (Exception e) {
            checkInterrupted(e);
            processException(e, sensorContext, inputFile);
            LOGGER.error("Unable to analyse file: " + inputFile.uri(), e);
        }
    }


    private void processRecognitionException(SightlyCompilerException e, SensorContext sensorContext, InputFile inputFile) {
        if (parsingErrorRuleKey != null) {
            processRecognitionExceptionForCustomRule(e, sensorContext, inputFile);
            return;
        }

        int lineOffset = 0;
        sensorContext.newAnalysisError()
                .onFile(inputFile)
                .at(inputFile.newPointer(e.getLine(), lineOffset))
                .message(e.getMessage())
                .save();
    }

    private void processRecognitionExceptionForCustomRule(SightlyCompilerException e, SensorContext sensorContext, InputFile inputFile) {
        NewIssue newIssue = sensorContext.newIssue();

        NewIssueLocation primaryLocation = newIssue.newLocation()
                .message("Parse error: " + e.getMessage())
                .on(inputFile)
                .at(inputFile.selectLine(e.getLine()));

        newIssue
                .forRule(parsingErrorRuleKey)
                .at(primaryLocation)
                .save();
    }

}
