/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Cognifide Limited
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

import com.cognifide.aemrules.htl.Htl;
import com.cognifide.aemrules.htl.api.HtlCheck;
import com.cognifide.aemrules.htl.checks.AvoidExtraSlyTagsCheck;
import com.cognifide.aemrules.htl.checks.AvoidUsingUnsafeDisplayContextCheck;
import com.cognifide.aemrules.htl.checks.CamelCaseCheck;
import com.cognifide.aemrules.htl.checks.DefaultDisplayContextCheck;
import com.cognifide.aemrules.htl.checks.DisplayContextInConditionalCommentsCheck;
import com.cognifide.aemrules.htl.checks.EventMandatoryDisplayContextCheck;
import com.cognifide.aemrules.htl.checks.ExplicitNamesInLoopsCheck;
import com.cognifide.aemrules.htl.checks.HtlAttributesShouldBeAtTheEndCheck;
import com.cognifide.aemrules.htl.checks.HtlCommentsCheck;
import com.cognifide.aemrules.htl.checks.InlineStyleMandatoryDisplayContextCheck;
import com.cognifide.aemrules.htl.checks.NamingAndReusingConditionsCheck;
import com.cognifide.aemrules.htl.checks.ParsingErrorCheck;
import com.cognifide.aemrules.htl.checks.PlaceTemplatesInSeparateFilesCheck;
import com.cognifide.aemrules.htl.checks.ScriptsAndStyleMandatoryDisplayContextCheck;
import com.cognifide.aemrules.htl.checks.SlyElementsAreAutomaticallyUnwrappedCheck;
import com.cognifide.aemrules.htl.checks.UseMostRestrictiveHtlContextCheck;
import com.cognifide.aemrules.htl.checks.UseSlyTagsOverRedundantMarkupCheck;
import org.sonar.check.Rule;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class HtlCheckClasses {

    public static final String REPOSITORY_KEY = Htl.KEY;

    public static final String REPOSITORY_NAME = "AEM Rules";

    private static final List<Class<? extends HtlCheck>> CLASSES = Arrays.asList(
            ParsingErrorCheck.class,
            HtlCommentsCheck.class,
            AvoidExtraSlyTagsCheck.class,
            AvoidUsingUnsafeDisplayContextCheck.class,
            DefaultDisplayContextCheck.class,
            DisplayContextInConditionalCommentsCheck.class,
            EventMandatoryDisplayContextCheck.class,
            HtlAttributesShouldBeAtTheEndCheck.class,
            InlineStyleMandatoryDisplayContextCheck.class,
            NamingAndReusingConditionsCheck.class,
            PlaceTemplatesInSeparateFilesCheck.class,
            ScriptsAndStyleMandatoryDisplayContextCheck.class,
            UseMostRestrictiveHtlContextCheck.class,
            UseSlyTagsOverRedundantMarkupCheck.class,
            CamelCaseCheck.class,
            SlyElementsAreAutomaticallyUnwrappedCheck.class,
            ExplicitNamesInLoopsCheck.class
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
