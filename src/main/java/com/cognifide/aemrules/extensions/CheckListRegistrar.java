package com.cognifide.aemrules.extensions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import com.cognifide.aemrules.checks.AnnotationsConstantsCheck;
import com.cognifide.aemrules.checks.ConstantsCheck;
import com.cognifide.aemrules.checks.InjectorShouldBeClosedCheck;
import com.cognifide.aemrules.checks.InjectorTryWithResourcesCheck;
import com.cognifide.aemrules.checks.PreferSlingServletAnnotation;
import com.cognifide.aemrules.checks.ResourceResolverShouldBeClosed;
import com.cognifide.aemrules.checks.SessionShouldBeLoggedOut;
import com.cognifide.aemrules.checks.ThreadSafeFieldCheck;
import com.cognifide.aemrules.checks.UnsynchronizedModificationCheck;

public class CheckListRegistrar implements CheckRegistrar {

	public static final String REPOSITORY_KEY = "AEM Rules";

	public static Class[] getCheckClasses() {
		// @formatter:off
		return new Class[] {
				ConstantsCheck.class,
				AnnotationsConstantsCheck.class,
				ThreadSafeFieldCheck.class,
				InjectorShouldBeClosedCheck.class,
				InjectorTryWithResourcesCheck.class,
				ResourceResolverShouldBeClosed.class,
				SessionShouldBeLoggedOut.class,
				PreferSlingServletAnnotation.class,
				UnsynchronizedModificationCheck.class
		};
		// @formatter:on
	}

	@Override
	public void register(RegistrarContext registrarContext) {
		List checkClasses = Arrays.asList(getCheckClasses());
		registrarContext.registerClassesForRepository(REPOSITORY_KEY, checkClasses,
				Collections.<Class<? extends JavaCheck>>emptyList());
	}
}
