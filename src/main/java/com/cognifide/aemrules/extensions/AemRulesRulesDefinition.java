/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
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

import org.sonar.api.server.rule.RulesDefinition;

public class AemRulesRulesDefinition implements RulesDefinition {

    @Override
    public void define(Context context) {
        NewRepository repo = context.createRepository(CheckListRegistrar.REPOSITORY_KEY, "java");
        repo.setName(CheckListRegistrar.REPOSITORY_KEY);
        RulesLoader rulesLoader = new RulesLoader();
        rulesLoader.load(repo, CheckListRegistrar.CHECK_CLASSES);
        repo.done();
    }

}
