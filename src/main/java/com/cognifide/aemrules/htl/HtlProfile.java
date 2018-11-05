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

import com.cognifide.aemrules.htl.rules.HtlCheckClasses;
import java.util.Objects;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.check.Rule;

public class HtlProfile implements BuiltInQualityProfilesDefinition {

    private static final String QUALITY_PROFILE_NAME = "AEM HTL Profile";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile htl = context
            .createBuiltInQualityProfile(QUALITY_PROFILE_NAME, Htl.KEY);
        HtlCheckClasses.getCheckClasses().stream()
            .map(HtlCheckClasses::getRule)
            .map(Rule::key)
            .filter(Objects::nonNull)
            .forEach(ruleKey -> htl.activateRule(HtlCheckClasses.REPOSITORY_KEY, ruleKey));
        htl.done();
    }
}
