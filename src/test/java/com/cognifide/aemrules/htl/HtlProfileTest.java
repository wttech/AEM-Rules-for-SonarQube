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

import static org.fest.assertions.Assertions.assertThat;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.BuiltInActiveRule;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.BuiltInQualityProfile;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.Context;

public class HtlProfileTest {

    private HtlProfile profile;

    private Context context;

    @Before
    public void setUp() {
        profile = new HtlProfile();
        this.context = new BuiltInQualityProfilesDefinition.Context();
    }

    @Test
    public void rulesLoaded() {
        profile.define(context);
        Map<String, BuiltInActiveRule> activeRules = getActiveRulesByRuleKey(context);

        assertThat(activeRules.size()).isGreaterThan(0);
        assertThat(activeRules.keySet()).contains("HTL-0");
    }

    private Map<String, BuiltInActiveRule> getActiveRulesByRuleKey(Context context) {
        return context.profilesByLanguageAndName().entrySet().stream()
            .map(Entry::getValue)
            .map(Map::entrySet)
            .flatMap(Collection::stream)
            .map(Entry::getValue)
            .map(BuiltInQualityProfile::rules)
            .flatMap(Collection::stream)
            .collect(Collectors.toMap(BuiltInActiveRule::ruleKey, Function.identity()));
    }

}