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
package com.vml.aemrules.java.profiles;

import com.vml.aemrules.java.rules.JavaRulesList;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Objects;

import static com.vml.aemrules.java.Constants.LANGUAGE_KEY;
import static com.vml.aemrules.java.Constants.REPOSITORY_KEY;

/**
 * Built-in quality profile for the <strong>Java</strong> language ({@link com.vml.aemrules.java.Constants#LANGUAGE_KEY}):
 * activates every {@link JavaRulesList#getJavaChecks()} rule in repository
 * {@link com.vml.aemrules.java.Constants#REPOSITORY_KEY}.
 * <p>
 * The <strong>HTL</strong> built-in profile ({@value com.vml.aemrules.htl.HtlProfile#QUALITY_PROFILE_NAME} on language
 * {@link com.vml.aemrules.htl.Htl#LANGUAGE_KEY}) is defined separately by {@link com.vml.aemrules.htl.HtlProfile}.
 * Both extensions are registered in {@link com.vml.aemrules.AemRulesSonarPlugin} so Java and HTL analyses each get a
 * dedicated “all AEM rules” profile.
 */
public class AemJavaProfile implements BuiltInQualityProfilesDefinition {

    public static final String QUALITY_PROFILE_NAME = "AEM Java";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context
                .createBuiltInQualityProfile(QUALITY_PROFILE_NAME, LANGUAGE_KEY);
        JavaRulesList.getJavaChecks().stream()
                .map(AemJavaProfile::ruleKey)
                .filter(Objects::nonNull)
                .forEach(ruleKey -> profile.activateRule(REPOSITORY_KEY, ruleKey));
        profile.done();
    }

    private static String ruleKey(Class<? extends JavaCheck> clazz) {
        Rule rule = clazz.getAnnotation(Rule.class);
        return rule == null ? null : rule.key();
    }
}
