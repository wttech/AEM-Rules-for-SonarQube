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
package com.cognifide.aemrules;

import com.cognifide.aemrules.extensions.AemRulesRulesDefinition;
import com.cognifide.aemrules.java.rules.JavaCheckClasses;
import com.cognifide.aemrules.htl.Htl;
import com.cognifide.aemrules.htl.Constants;
import com.cognifide.aemrules.htl.HtlProfile;
import com.cognifide.aemrules.htl.HtlSensor;
import com.google.common.collect.ImmutableList;
import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class AemRulesSonarPlugin implements Plugin {

    private static ImmutableList<PropertyDefinition> pluginProperties() {
        return ImmutableList.of(
            PropertyDefinition.builder(Constants.FILE_EXTENSIONS_PROP_KEY)
                .name("File suffixes")
                .description("List of file suffixes that will be scanned.")
                .category(Htl.NAME)
                .defaultValue(Constants.FILE_EXTENSIONS_DEF_VALUE)
                .onQualifiers(Qualifiers.PROJECT)
                .multiValues(true)
                .build(),
            PropertyDefinition.builder(Constants.HTL_FILES_RELATIVE_PATHS_KEY)
                .name("HTL files relative paths")
                .description("List of relative paths that contains HTL files.")
                .category(Htl.NAME)
                .defaultValue(Constants.HTL_FILES_RELATIVE_PATHS_DEF_VALUE)
                .onQualifiers(Qualifiers.PROJECT)
                .multiValues(true)
                .build()
        );
    }

    @Override
    public void define(Context context) {
        context.addExtensions(
            Htl.class,
            AemRulesRulesDefinition.class,
            JavaCheckClasses.class,
            HtlProfile.class,
            HtlSensor.class);
        context.addExtensions(pluginProperties());
    }
}
