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
import com.cognifide.aemrules.htl.lex.HtlLexer;
import com.cognifide.aemrules.htl.visitors.DefaultHtlVisitor;
import com.cognifide.aemrules.htl.visitors.HtlScanner;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.visitor.HtmlSourceCode;
import org.sonar.squidbridge.ProgressReport;

@Phase(name = Phase.Name.PRE)
public class HtlSensor extends HtlFilesAnalyzer implements Sensor {

    private static final HtlLexer lexer = new HtlLexer();

    private final FileLinesContextFactory fileLinesContextFactory;

    private Configuration configuration;

    public HtlSensor(FileLinesContextFactory fileLinesContextFactory, Configuration configuration, CheckFactory checkFactory) {
        super(checkFactory);
        this.configuration = configuration;
        this.fileLinesContextFactory = fileLinesContextFactory;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
            .name(Htl.NAME)
            .onlyOnFileType(Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {
        FilePredicate htlFilePredicate = HtlFilePredicateProvider.createFilePredicate(configuration, context);
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

    @Override
    void scanFile(SensorContext sensorContext, InputFile inputFile) throws IOException {
        final HtlScanner scanner = setupScanner();
        HtmlSourceCode sourceCode = new HtmlSourceCode(inputFile);

        try (Reader reader = new StringReader(inputFile.contents())) {
            List<Node> nodeList = lexer.parse(reader);
            scanner.scan(nodeList, sourceCode);
            MetricsSaver.saveIssues(sensorContext, sourceCode);
            MetricsSaver.saveMeasures(sensorContext, sourceCode);
            MetricsSaver.saveLineLevelMeasures(inputFile, sourceCode, fileLinesContextFactory);
        }
    }

    /**
     * Create HtlScanner with Visitors.
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
