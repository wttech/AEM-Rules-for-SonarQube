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
import com.cognifide.aemrules.htl.api.ParsingErrorRule;
import com.cognifide.aemrules.htl.lex.HtlLexer;
import com.cognifide.aemrules.htl.rules.HtlCheckClasses;
import com.cognifide.aemrules.htl.visitors.DefaultHtlVisitor;
import com.cognifide.aemrules.htl.visitors.HtlScanner;
import com.google.common.base.Throwables;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.scripting.sightly.compiler.SightlyCompilerException;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugins.html.checks.HtmlIssue;
import org.sonar.plugins.html.visitor.HtmlSourceCode;
import org.sonar.squidbridge.ProgressReport;
import org.sonar.squidbridge.api.AnalysisException;

@Phase(name = Phase.Name.PRE)
public class HtlSensor implements Sensor {

    private static final Logger LOGGER = Loggers.get(HtlSensor.class);

    private static final HtlLexer lexer = new HtlLexer();

    private Configuration configuration;

    private final HtlChecks checks;

    private final FileLinesContextFactory fileLinesContextFactory;

    private RuleKey parsingErrorRuleKey;

    public HtlSensor(FileLinesContextFactory fileLinesContextFactory, Configuration configuration, CheckFactory checkFactory) {
        this.configuration = configuration;
        this.checks = HtlChecks.createHtlCheck(checkFactory)
            .addChecks(HtlCheckClasses.REPOSITORY_KEY, HtlCheckClasses.getCheckClasses());
        this.parsingErrorRuleKey = setupParsingErrorRuleKey(checks);
        this.fileLinesContextFactory = fileLinesContextFactory;
    }

    private RuleKey setupParsingErrorRuleKey(HtlChecks checks) {
        return checks.getAll().stream()
            .filter(check -> check.getClass().isAnnotationPresent(ParsingErrorRule.class))
            .findFirst()
            .map(checks::ruleKeyFor)
            .orElse(null);
    }

    private static FilePredicate createFilePredicate(Configuration configuration, SensorContext sensorContext) {
        FilePredicates predicates = sensorContext.fileSystem().predicates();

        List<FilePredicate> fileExtensions = getFileExtensionsPredicates(predicates, configuration);
        List<FilePredicate> relativePaths = getPathsPredicate(predicates, configuration);

        return predicates.and(
            predicates.hasType(InputFile.Type.MAIN),
            predicates.or(
                predicates.hasLanguages(Htl.KEY),
                predicates.or(fileExtensions)
            ),
            predicates.or(relativePaths)
        );
    }

    private static List<FilePredicate> getFileExtensionsPredicates(FilePredicates predicates, Configuration configuration) {
        return Stream.of(configuration.getStringArray(Constants.FILE_EXTENSIONS_PROP_KEY))
            .filter(Objects::nonNull)
            .map(extension -> StringUtils.removeStart(extension, "."))
            .map(predicates::hasExtension)
            .collect(Collectors.toList());
    }

    private static List<FilePredicate> getPathsPredicate(FilePredicates predicates, Configuration configuration) {
        return Stream.of(configuration.getStringArray(Constants.HTL_FILES_RELATIVE_PATHS_KEY))
            .filter(StringUtils::isNotEmpty)
            .map(path -> path.concat("/**"))
            .map(predicates::matchesPathPattern)
            .collect(Collectors.toList());
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

    private static void processException(Exception e, SensorContext sensorContext,
        InputFile inputFile) {
        sensorContext.newAnalysisError()
            .onFile(inputFile)
            .message(e.getMessage())
            .save();
    }

    private static void saveMetrics(SensorContext context, HtmlSourceCode sourceCode) {
        InputFile inputFile = sourceCode.inputFile();

        for (Map.Entry<Metric<Integer>, Integer> entry : sourceCode.getMeasures().entrySet()) {
            context.<Integer>newMeasure()
                .on(inputFile)
                .forMetric(entry.getKey())
                .withValue(entry.getValue())
                .save();
        }

        for (HtmlIssue issue : sourceCode.getIssues()) {
            NewIssue newIssue = context.newIssue()
                .forRule(issue.ruleKey())
                .gap(issue.cost());
            Integer line = issue.line();
            NewIssueLocation location = newIssue.newLocation()
                .on(inputFile)
                .message(issue.message());
            if (line != null) {
                location.at(inputFile.selectLine(line));
            }
            newIssue.at(location);
            newIssue.save();
        }
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
            .name(Htl.NAME)
            .onlyOnFileType(Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {
        FilePredicate htlFilePredicate = createFilePredicate(configuration, context);
        Iterable<InputFile> inputFiles = context.fileSystem().inputFiles(htlFilePredicate);

        Collection<File> files = StreamSupport.stream(inputFiles.spliterator(), false)
            .map(InputFile::uri)
            .map(File::new)
            .collect(Collectors.toList());

        ProgressReport progressReport = new ProgressReport("Report about progress of HTL analyzer",
            TimeUnit.SECONDS.toMillis(10));
        progressReport.start(files);
        analyseFiles(context, inputFiles, progressReport);
    }

    private void analyseFiles(SensorContext context, Iterable<InputFile> inputFiles, ProgressReport progressReport) {
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

    private void analyse(SensorContext sensorContext, InputFile inputFile) {
        try {
            scanFile(sensorContext, inputFile);
        } catch (SightlyCompilerException e) {
            checkInterrupted(e);
            LOGGER.error("Unable to parse file: " + inputFile.uri());
            LOGGER.error(e.getMessage());
            processRecognitionException(e, sensorContext, inputFile);
        } catch (Exception e) {
            checkInterrupted(e);
            processException(e, sensorContext, inputFile);
            LOGGER.error("Unable to analyse file: " + inputFile.uri(), e);
        }
    }

    private void processRecognitionException(SightlyCompilerException e, SensorContext sensorContext, InputFile inputFile) {
        if (parsingErrorRuleKey != null) {
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

        sensorContext.newAnalysisError()
            .onFile(inputFile)
            .at(inputFile.newPointer(e.getLine(), 0))
            .message(e.getMessage())
            .save();

    }

    private void scanFile(SensorContext sensorContext, InputFile inputFile) throws IOException {
        final HtlScanner scanner = setupScanner();
        HtmlSourceCode sourceCode = new HtmlSourceCode(inputFile);

        Reader reader = new StringReader(inputFile.contents());
        scanner.scan(lexer.parse(reader), sourceCode);
        saveMetrics(sensorContext, sourceCode);
        saveLineLevelMeasures(inputFile, sourceCode);
    }

    private void saveLineLevelMeasures(InputFile inputFile, HtmlSourceCode htmlSourceCode) {
        FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(inputFile);

        for (Integer line : htmlSourceCode.getDetailedLinesOfCode()) {
            fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, 1);
        }

        fileLinesContext.save();
    }

    /**
     * Create PageScanner with Visitors.
     */
    private HtlScanner setupScanner() {
        HtlScanner scanner = new HtlScanner();

        for (HtlCheck check : checks.getAll()) {
            RuleKey ruleKey = checks.ruleKeyFor(check);
            check.setRuleKey(ruleKey);
            if (check instanceof DefaultHtlVisitor) {
                DefaultHtlVisitor nodeVisitor = (DefaultHtlVisitor) check;
                scanner.addVisitor(nodeVisitor);
            }
        }
        return scanner;
    }

}
