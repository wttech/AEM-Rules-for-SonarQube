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
package com.cognifide.aemrules.java.rules;

import com.cognifide.aemrules.java.checks.AdministrativeAccessUsageCheck;
import com.cognifide.aemrules.java.checks.AnnotationsConstantsCheck;
import com.cognifide.aemrules.java.checks.ConstantsCheck;
import com.cognifide.aemrules.java.checks.ContentResourceShouldBeNullCheckedCheck;
import com.cognifide.aemrules.java.checks.HttpConstantCheck;
import com.cognifide.aemrules.java.checks.InjectorShouldBeClosedCheck;
import com.cognifide.aemrules.java.checks.InjectorTryWithResourcesCheck;
import com.cognifide.aemrules.java.checks.ModifiableValueMapUsageCheck;
import com.cognifide.aemrules.java.checks.PreferSlingServletAnnotation;
import com.cognifide.aemrules.java.checks.SessionShouldBeLoggedOut;
import com.cognifide.aemrules.java.checks.SynchronizedKeywordUsageCheck;
import com.cognifide.aemrules.java.checks.ThreadSafeFieldCheck;
import com.cognifide.aemrules.java.checks.resourceresolver.close.ResourceResolverShouldBeClosed;
import com.cognifide.aemrules.java.checks.slice.iterator.IteratingResourcesCheck;
import com.cognifide.aemrules.java.checks.slice.jcrproperty.JcrPropertyFieldsInConstructorCheck;
import com.cognifide.aemrules.java.checks.slice.session.ModelsShouldNotUseSessionCheck;
import com.cognifide.aemrules.java.checks.slingmodels.DefaultInjectionStrategyAnnotationCheck;
import com.cognifide.aemrules.java.checks.slingquery.SlingQueryImplicitStrategyCheck;
import com.cognifide.aemrules.version.AemVersion;
import com.cognifide.aemrules.version.VersionSupportChecker;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.sonar.api.config.Configuration;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

public class JavaCheckClasses implements CheckRegistrar {

    public static final String REPOSITORY_KEY = "AEM Rules";

    public static final String REPOSITORY_NAME = "AEM Rules";

    public static final List<Class<? extends JavaCheck>> CHECK_CLASSES
        = ImmutableList.of(
        AdministrativeAccessUsageCheck.class,
        AnnotationsConstantsCheck.class,
        ConstantsCheck.class,
        HttpConstantCheck.class,
        InjectorShouldBeClosedCheck.class,
        InjectorTryWithResourcesCheck.class,
        ModelsShouldNotUseSessionCheck.class,
        IteratingResourcesCheck.class,
        JcrPropertyFieldsInConstructorCheck.class,
        PreferSlingServletAnnotation.class,
        ResourceResolverShouldBeClosed.class,
        SessionShouldBeLoggedOut.class,
        SynchronizedKeywordUsageCheck.class,
        ThreadSafeFieldCheck.class,
        DefaultInjectionStrategyAnnotationCheck.class,
        ModifiableValueMapUsageCheck.class,
        ContentResourceShouldBeNullCheckedCheck.class,
        SlingQueryImplicitStrategyCheck.class
    );

    private final Configuration configuration;

    public JavaCheckClasses(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void register(RegistrarContext registrarContext) {
        String aemVersion = configuration.get(VersionSupportChecker.VERSION_PROPERTY)
            .orElse(VersionSupportChecker.DEFAULT_AEM_VERSION);
        List<Class<? extends JavaCheck>> checkClassesToRegister = CHECK_CLASSES.stream()
            .filter(checkClass -> shouldRegister(aemVersion, checkClass))
            .collect(Collectors.toList());
        registrarContext.registerClassesForRepository(REPOSITORY_KEY, checkClassesToRegister,
            Collections.emptyList());
    }

    private boolean shouldRegister(String aemVersion, Class<? extends JavaCheck> checkClass) {
        return Optional.ofNullable(checkClass.getAnnotation(AemVersion.class))
            .map(supportedVersion -> VersionSupportChecker.create(supportedVersion).supports(aemVersion))
            .orElse(true);
    }
}
