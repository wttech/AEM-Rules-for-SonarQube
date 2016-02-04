package com.cognifide.aemrules.extensions;

import java.util.Collections;
import java.util.List;

import com.cognifide.aemrules.checks.HttpConstantCheck;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import com.cognifide.aemrules.checks.AdministrativeAccessUsageCheck;
import com.cognifide.aemrules.checks.AnnotationsConstantsCheck;
import com.cognifide.aemrules.checks.ConstantsCheck;
import com.cognifide.aemrules.checks.InjectorShouldBeClosedCheck;
import com.cognifide.aemrules.checks.InjectorTryWithResourcesCheck;
import com.cognifide.aemrules.checks.ModelsShouldNotUseSessionCheck;
import com.cognifide.aemrules.checks.IteratingResourcesCheck;
import com.cognifide.aemrules.checks.JcrPropertyFieldsInConstructorCheck;
import com.cognifide.aemrules.checks.PreferSlingServletAnnotation;
import com.cognifide.aemrules.checks.ResourceResolverShouldBeClosed;
import com.cognifide.aemrules.checks.SessionShouldBeLoggedOut;
import com.cognifide.aemrules.checks.SynchornizedKeywordUsageCheck;
import com.cognifide.aemrules.checks.ThreadSafeFieldCheck;
import com.google.common.collect.ImmutableList;

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
		.build();

	@Override
	public void register(RegistrarContext registrarContext) {
		registrarContext.registerClassesForRepository(REPOSITORY_KEY, CHECK_CLASSES,
			Collections.<Class<? extends JavaCheck>>emptyList());
	}
}
