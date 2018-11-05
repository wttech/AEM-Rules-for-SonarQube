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
package com.cognifide.aemrules.htl.rules;

import com.cognifide.aemrules.htl.checks.HtlAttributesShouldBeAtTheEndCheck;
import com.cognifide.aemrules.htl.checks.ParsingErrorCheck;
import com.cognifide.aemrules.htl.Htl;
import com.cognifide.aemrules.htl.api.HtlCheck;
import com.cognifide.aemrules.htl.checks.PlaceTemplatesInSeparateFilesCheck;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import org.sonar.check.Rule;

public final class HtlCheckClasses {

    public static final String REPOSITORY_KEY = Htl.KEY;

    public static final String REPOSITORY_NAME = "AEM Rules";

    private static final List<Class<? extends HtlCheck>> CLASSES = ImmutableList.of(
        ParsingErrorCheck.class,
        HtlAttributesShouldBeAtTheEndCheck.class,
        PlaceTemplatesInSeparateFilesCheck.class
    );

    private HtlCheckClasses() {
        //private constructor
    }

    public static List<Class<? extends HtlCheck>> getCheckClasses() {
        return CLASSES;
    }

    public static Rule getRule(Class<? extends HtlCheck> clazz) {
        return Optional.ofNullable(clazz)
            .filter(c -> c.isAnnotationPresent(Rule.class))
            .map(c -> c.getAnnotation(Rule.class))
            .orElse(null);
    }
}
