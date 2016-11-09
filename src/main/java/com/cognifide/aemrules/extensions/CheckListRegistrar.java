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

import com.cognifide.aemrules.checks.AdministrativeAccessUsageCheck;
import com.cognifide.aemrules.checks.AnnotationsConstantsCheck;
import com.cognifide.aemrules.checks.ConstantsCheck;
import com.cognifide.aemrules.checks.HttpConstantCheck;
import com.cognifide.aemrules.checks.InjectorShouldBeClosedCheck;
import com.cognifide.aemrules.checks.InjectorTryWithResourcesCheck;
import com.cognifide.aemrules.checks.ModifiableValueMapUsageCheck;
import com.cognifide.aemrules.checks.PreferSlingServletAnnotation;
import com.cognifide.aemrules.checks.SessionShouldBeLoggedOut;
import com.cognifide.aemrules.checks.SynchornizedKeywordUsageCheck;
import com.cognifide.aemrules.checks.ThreadSafeFieldCheck;
import com.cognifide.aemrules.checks.resourceresolver.close.ResourceResolverShouldBeClosed;
import com.cognifide.aemrules.checks.slice.iterator.IteratingResourcesCheck;
import com.cognifide.aemrules.checks.slice.jcrproperty.JcrPropertyFieldsInConstructorCheck;
import com.cognifide.aemrules.checks.slice.session.ModelsShouldNotUseSessionCheck;
import com.cognifide.aemrules.checks.slingmodels.DefaultInjectionStrategyAnnotationCheck;
import com.cognifide.aemrules.checks.slingquery.SlingQueryImplicitStrategyCheck;
import com.google.common.collect.ImmutableList;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Collections;
import java.util.List;

public class CheckListRegistrar implements CheckRegistrar {

	public static final String REPOSITORY_KEY = "AEM Rules";

	public static final List<Class<? extends JavaCheck>> CHECK_CLASSES
		= ImmutableList.<Class<? extends JavaCheck>>builder()
		.add(AdministrativeAccessUsageCheck.class)
		.add(AnnotationsConstantsCheck.class)
		.add(ConstantsCheck.class)
		.add(HttpConstantCheck.class)
		.add(InjectorShouldBeClosedCheck.class)
		.add(InjectorTryWithResourcesCheck.class)
		.add(ModelsShouldNotUseSessionCheck.class)
		.add(IteratingResourcesCheck.class)
		.add(JcrPropertyFieldsInConstructorCheck.class)
		.add(PreferSlingServletAnnotation.class)
		.add(ResourceResolverShouldBeClosed.class)
		.add(SessionShouldBeLoggedOut.class)
		.add(SynchornizedKeywordUsageCheck.class)
		.add(ThreadSafeFieldCheck.class)
		.add(DefaultInjectionStrategyAnnotationCheck.class)
		.add(ModifiableValueMapUsageCheck.class)
		.add(SlingQueryImplicitStrategyCheck.class)
		.build();

	@Override
	public void register(RegistrarContext registrarContext) {
		registrarContext.registerClassesForRepository(REPOSITORY_KEY, CHECK_CLASSES,
			Collections.<Class<? extends JavaCheck>>emptyList());
	}
}
