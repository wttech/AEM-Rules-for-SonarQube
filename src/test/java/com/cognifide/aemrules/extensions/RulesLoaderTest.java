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
package com.cognifide.aemrules.extensions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import com.cognifide.aemrules.java.rules.JavaCheckClasses;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.NewRepository;
import org.sonar.api.server.rule.RulesDefinition.RepositoryImpl;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;

public class RulesLoaderTest {

    private static final String RULE_KEY = "AEM-TEST";

    private static final String RULE_NAME = "test";

    private static final String TECHNICAL_DEBT = "10min";

    private static final String RULE_MARKDOWN_TEST_DESCRIPTION = "Test description.";

    private static final String RULE_PROPERTY_KEY = "testKey";

    private static final String RULE_PROPERTY_DESCRIPTION = "testDescription";

    private static final String RULES_PROPERTY_DEFAULT_VALUE = "testDefault";

    private static final String RULES_PROPERTY_TYPE = "testType";

    private NewRepository repo;

    private RulesDefinition.Context context;

    @Before
    public void setUp() {
        context = new RulesDefinition.Context();
        repo = context.createRepository(JavaCheckClasses.REPOSITORY_KEY, "java");
        repo.setName(JavaCheckClasses.REPOSITORY_KEY);
    }

    @Test
    public void shouldLoadRuleWithAllSettings() {
        givenRulesLoaded(ImmutableList.of(RuleWithAllSettings.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule(RULE_KEY);

        Assert.assertThat(rule.markdownDescription(), is(RULE_MARKDOWN_TEST_DESCRIPTION));
        Assert.assertThat(rule.name(), is(RULE_NAME));
        Assert.assertThat(rule.severity(), is(Priority.MINOR.toString()));
        Assert.assertThat(rule.tags().contains(Tags.AEM), is(true));
        Assert.assertThat(rule.debtRemediationFunction().baseEffort(), is(TECHNICAL_DEBT));
    }

    @Test
    public void shouldNotSetTechnicalDebtWhenAnnotationNotPresent() {
        givenRulesLoaded(ImmutableList.of(RuleWithoutMetadataAnnotation.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule(RULE_KEY);

        Assert.assertThat(rule.markdownDescription(), is(RULE_MARKDOWN_TEST_DESCRIPTION));
        Assert.assertThat(rule.name(), is(RULE_NAME));
        Assert.assertThat(rule.severity(), is(Priority.MINOR.toString()));
        Assert.assertThat(rule.tags().contains(Tags.AEM), is(true));
        Assert.assertThat(rule.debtRemediationFunction(), is(nullValue()));
    }

    @Test
    public void shouldNotSetTechnicalDebtWhenTechnicalDebtNotSetInMetadata() {
        givenRulesLoaded(ImmutableList.of(RuleWithEmptyTechnicalDebt.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule(RULE_KEY);

        Assert.assertThat(rule.markdownDescription(), is(RULE_MARKDOWN_TEST_DESCRIPTION));
        Assert.assertThat(rule.name(), is(RULE_NAME));
        Assert.assertThat(rule.severity(), is(Priority.MINOR.toString()));
        Assert.assertThat(rule.tags().contains(Tags.AEM), is(true));
        Assert.assertThat(rule.debtRemediationFunction(), is(nullValue()));
    }

    @Test
    public void shouldNotLoadRuleWhenRuleAnnotationIsNotPresent() {
        givenRulesLoaded(ImmutableList.of(RuleWithoutRuleAnnotation.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule(RULE_KEY);

        Assert.assertThat(rule, is(nullValue()));
    }


    @Test
    public void shouldSetDefaultValuesWhenRuleAttributeWithNameOnly() {
        givenRulesLoaded(ImmutableList.of(RuleWithOnlyNameAttribute.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule("com.cognifide.aemrules.extensions.RulesLoaderTest.RuleWithOnlyNameAttribute");

        Assert.assertThat(rule.markdownDescription(), is("No description yet."));
        Assert.assertThat(rule.name(), is(RULE_NAME));
        Assert.assertThat(rule.severity(), is(Priority.MAJOR.toString()));
        Assert.assertThat(rule.tags().size(), is(0));
        Assert.assertThat(rule.debtRemediationFunction().baseEffort(), is(TECHNICAL_DEBT));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenRuleNameNotProvide() {
        givenRulesLoaded(ImmutableList.of(RuleWithoutNameAttributeSet.class));
    }

    @Test
    public void shouldLoadRuleWithProperty() {
        givenRulesLoaded(ImmutableList.of(RuleWithRuleProperty.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule(RULE_KEY);
        RulesDefinition.Param param = rule.param(RULE_PROPERTY_KEY);

        Assert.assertThat(param.description(), is(RULE_PROPERTY_DESCRIPTION));
        Assert.assertThat(param.defaultValue(), is(RULES_PROPERTY_DEFAULT_VALUE));
        Assert.assertThat(param.type().type(), is(RULES_PROPERTY_TYPE));
    }

    @Test
    public void shouldLoadRuleWithPropertyWithoutAttributes() {
        givenRulesLoaded(ImmutableList.of(RuleWithRulePropertyWithoutAttributes.class));

        RepositoryImpl repository = (RepositoryImpl) context.repository(JavaCheckClasses.REPOSITORY_KEY);
        RulesDefinition.Rule rule = repository.rule(RULE_KEY);
        RulesDefinition.Param param = rule.param("testProperty");

        Assert.assertThat(param.description(), is(nullValue()));
        Assert.assertThat(param.defaultValue(), is(nullValue()));
        Assert.assertThat(param.type().type(), is("STRING"));
    }

    private void givenRulesLoaded(List<Class<? extends JavaCheck>> annotationClasses) {
        RulesLoader rulesLoader = new RulesLoader();
        rulesLoader.load(repo, annotationClasses);
        repo.done();
    }

    @Rule(
        key = RULE_KEY,
        name = RULE_NAME,
        priority = Priority.MINOR,
        tags = Tags.AEM
    )
    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = TECHNICAL_DEBT
    )
    private class RuleWithAllSettings extends BaseTreeVisitor implements JavaFileScanner {

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @Rule(
        key = RULE_KEY,
        name = RULE_NAME,
        priority = Priority.MINOR,
        tags = Tags.AEM
    )
    @AemVersion(
        all = true
    )
    private class RuleWithoutMetadataAnnotation extends BaseTreeVisitor implements JavaFileScanner {

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @Rule(
        key = RULE_KEY,
        name = RULE_NAME,
        priority = Priority.MINOR,
        tags = Tags.AEM
    )
    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = ""
    )
    private class RuleWithEmptyTechnicalDebt extends BaseTreeVisitor implements JavaFileScanner {

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = TECHNICAL_DEBT
    )
    private class RuleWithoutRuleAnnotation extends BaseTreeVisitor implements JavaFileScanner {

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @Rule(
        name = RULE_NAME
    )
    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = TECHNICAL_DEBT
    )
    private class RuleWithOnlyNameAttribute extends BaseTreeVisitor implements JavaFileScanner {

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @Rule(
        key = RULE_KEY,
        priority = Priority.MINOR,
        tags = Tags.AEM
    )
    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = TECHNICAL_DEBT
    )
    private class RuleWithoutNameAttributeSet extends BaseTreeVisitor implements JavaFileScanner {

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @Rule(
        key = RULE_KEY,
        name = RULE_NAME,
        priority = Priority.MINOR,
        tags = Tags.AEM
    )
    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = TECHNICAL_DEBT
    )
    private class RuleWithRuleProperty extends BaseTreeVisitor implements JavaFileScanner {

        @RuleProperty(
            key = RULE_PROPERTY_KEY,
            description = RULE_PROPERTY_DESCRIPTION,
            defaultValue = RULES_PROPERTY_DEFAULT_VALUE,
            type = RULES_PROPERTY_TYPE
        )
        private String testProperty;

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

    @Rule(
        key = RULE_KEY,
        name = RULE_NAME,
        priority = Priority.MINOR,
        tags = Tags.AEM
    )
    @AemVersion(
        all = true
    )
    @Metadata(
        technicalDebt = TECHNICAL_DEBT
    )
    private class RuleWithRulePropertyWithoutAttributes extends BaseTreeVisitor implements JavaFileScanner {

        @RuleProperty
        private String testProperty;

        @Override
        public void scanFile(JavaFileScannerContext javaFileScannerContext) {

        }
    }

}
