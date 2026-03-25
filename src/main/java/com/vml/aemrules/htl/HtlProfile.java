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

import com.vml.aemrules.htl.rules.HtlRulesList;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.check.Rule;

import java.util.Objects;

import static com.vml.aemrules.htl.Constants.REPOSITORY_KEY;

public class HtlProfile implements BuiltInQualityProfilesDefinition {

    public static final String QUALITY_PROFILE_NAME = "HTL";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile htl = context
                .createBuiltInQualityProfile(QUALITY_PROFILE_NAME, Htl.LANGUAGE_KEY);
        HtlRulesList.getChecks().stream()
                .map(HtlRulesList::getRule)
                .map(Rule::key)
                .filter(Objects::nonNull)
                .forEach(ruleKey -> htl.activateRule(REPOSITORY_KEY, ruleKey));
        htl.done();
    }
}
