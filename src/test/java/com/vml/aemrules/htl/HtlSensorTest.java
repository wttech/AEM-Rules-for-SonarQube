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

import com.vml.aemrules.htl.checks.HtlAttributesShouldBeAtTheEndCheck;
import com.vml.aemrules.htl.rules.HtlRulesDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sonar.api.SonarRuntime;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.DefaultActiveRules;
import org.sonar.api.batch.rule.internal.NewActiveRule;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.utils.Version;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vml.aemrules.htl.Constants.REPOSITORY_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class HtlSensorTest {

    private static final File TEST_DIR = new File("src/test/resources/sensor");

    private HtlSensor sensor;

    private SensorContextTester tester;

    @BeforeEach
    void setUp() {
        SonarRuntime sonarRuntime = mock(SonarRuntime.class);
        when(sonarRuntime.getApiVersion()).thenReturn(Version.parse("13.5.0.4319"));
        RulesDefinition rulesDefinition = new HtlRulesDefinition(sonarRuntime);
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        Repository htlRepository = context.repository(REPOSITORY_KEY);

        FileLinesContextFactory fileLinesContextFactory = getMockedFileLinesContextFactory();
        Configuration configuration = getMockedConfiguration();
        CheckFactory checkFactory = getCheckFactory(htlRepository);

        sensor = new HtlSensor(fileLinesContextFactory, configuration, checkFactory);
        tester = SensorContextTester.create(TEST_DIR);
    }

    private CheckFactory getCheckFactory(Repository htlRepository) {
        List<NewActiveRule> ar = new ArrayList<>();
        for (RulesDefinition.Rule rule : htlRepository.rules()) {
            ar.add(new NewActiveRule.Builder().setRuleKey(RuleKey.of(REPOSITORY_KEY, rule.key())).build());
        }
        return new CheckFactory(new DefaultActiveRules(ar));
    }

    private Configuration getMockedConfiguration() {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getStringArray(Constants.FILE_EXTENSIONS_PROP_KEY)).thenReturn(Constants.FILE_EXTENSIONS_DEF_VALUE.split(","));
        when(configuration.getStringArray(Constants.HTL_FILES_RELATIVE_PATHS_KEY)).thenReturn(new String[]{});
        return configuration;
    }

    private FileLinesContextFactory getMockedFileLinesContextFactory() {
        FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
        when(fileLinesContextFactory.createFor(Mockito.any(InputFile.class))).thenReturn(mock(FileLinesContext.class));
        return fileLinesContextFactory;
    }

    @Test
    void checkIncorrectFile_issuesFound() throws Exception {
        DefaultInputFile inputFile = createInputFile(TEST_DIR, "test.html");
        tester.fileSystem().add(inputFile);
        sensor.execute(tester);
        assertThat(tester.allIssues()).isNotEmpty();
        List<String> issuesRules = tester.allIssues().stream()
                .map(Issue::ruleKey)
                .map(RuleKey::rule)
                .distinct()
                .collect(Collectors.toList());
        assertThat(issuesRules).contains(HtlAttributesShouldBeAtTheEndCheck.RULE_KEY);
    }

    @Test
    void checkCancellation_noIssueFound() throws Exception {
        DefaultInputFile inputFile = createInputFile(TEST_DIR, "test.html");
        tester.fileSystem().add(inputFile);
        tester.setCancelled(true);
        sensor.execute(tester);
        assertThat(tester.allIssues()).isEmpty();
    }

    @Test
    void sonarlintCheck() throws Exception {
        DefaultInputFile inputFile = createInputFile(TEST_DIR, "test.html");
        tester.fileSystem().add(inputFile);
        sensor.execute(tester);
        String componentKey = inputFile.key();
        assertThat(tester.allIssues()).isNotEmpty();
        assertThat(tester.cpdTokens(componentKey)).isNull();
        assertThat(tester.highlightingTypeAt(componentKey, 1, 0)).isEmpty();
    }

    @Test
    void checkFileWithError_parsingErrorRaisesHtl0Issue() throws Exception {
        DefaultInputFile inputFile = createInputFile(TEST_DIR, "error.html");
        tester.fileSystem().add(inputFile);
        sensor.execute(tester);
        assertThat(tester.allIssues()).anyMatch(i -> RuleKey.of(REPOSITORY_KEY, "HTL-0").equals(i.ruleKey()));
        assertThat(tester.allAnalysisErrors()).isEmpty();
    }

    @Test
    void checkExpressionWithinHtmlComment_parsingErrorRaisesHtl0Issue() throws Exception {
        DefaultInputFile inputFile = createInputFile(TEST_DIR, "comment.html");
        tester.fileSystem().add(inputFile);
        sensor.execute(tester);
        assertThat(tester.allIssues()).anyMatch(i -> RuleKey.of(REPOSITORY_KEY, "HTL-0").equals(i.ruleKey()));
        assertThat(tester.allAnalysisErrors()).isEmpty();
    }

    @Test
    void checkNestedTags_noErrorsFound() throws IOException {
        DefaultInputFile inputFile = createInputFile(TEST_DIR, "nestedTags.html");
        tester.fileSystem().add(inputFile);
        sensor.execute(tester);
        assertThat(tester.allAnalysisErrors()).isEmpty();
    }

    @Test
    void checkDescriptorConfiguration() {
        DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
        sensor.describe(descriptor);
        assertThat(descriptor.name()).isEqualTo("HTL");
        assertThat(descriptor.languages()).isEmpty();
    }

    private DefaultInputFile createInputFile(File dir, String fileName) throws IOException {
        return new TestInputFileBuilder("key", fileName)
                .setModuleBaseDir(dir.toPath())
                .setLanguage(Htl.LANGUAGE_KEY)
                .setType(InputFile.Type.MAIN)
                .initMetadata(new String(Files.readAllBytes(new File(dir, fileName).toPath()), StandardCharsets.UTF_8))
                .setCharset(StandardCharsets.UTF_8)
                .build();
    }

}
