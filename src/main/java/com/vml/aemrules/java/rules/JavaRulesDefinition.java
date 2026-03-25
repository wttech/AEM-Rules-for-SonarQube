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
package com.vml.aemrules.java.rules;

import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;
import org.sonarsource.api.sonarlint.SonarLintSide;

import java.util.ArrayList;

import static com.vml.aemrules.java.Constants.LANGUAGE_KEY;
import static com.vml.aemrules.java.Constants.REPOSITORY_KEY;
import static com.vml.aemrules.java.Constants.REPOSITORY_NAME;

@SonarLintSide
public class JavaRulesDefinition implements RulesDefinition {

    private static final String RESOURCE_BASE_PATH = "org/sonar/l10n/java/rules/java";

    private final SonarRuntime runtime;

    public JavaRulesDefinition(SonarRuntime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, LANGUAGE_KEY).setName(REPOSITORY_NAME);
        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, runtime);
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(JavaRulesList.getChecks()));
        repository.done();
    }
}
