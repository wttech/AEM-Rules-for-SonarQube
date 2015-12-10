package com.cognifide.aemrules.extensions;

import com.cognifide.aemrules.checks.*;
import org.sonar.plugins.java.api.CheckRegistrar;

import java.util.Arrays;
import java.util.List;

public class CheckListRegistrar implements CheckRegistrar {

	public static final String REPOSITORY_KEY = "AEM Rules";

	public static Class[] getCheckClasses() {
		return new Class[] {
				AnnotationsConstantsCheck.class,
				ConstantsCheck.class,
				InjectorShouldBeClosedCheck.class,
				InjectorTryWithResourcesCheck.class,
				ModelsShouldNotUseSessionCheck.class,
				PreferSlingServletAnnotation.class,
				ResourceResolverShouldBeClosed.class,
				SessionShouldBeLoggedOut.class,
				ThreadSafeFieldCheck.class
		};
	}

	@Override
	public void register(RegistrarContext registrarContext) {
		List checkClasses = Arrays.asList(getCheckClasses());
		registrarContext.registerClassesForRepository(REPOSITORY_KEY, checkClasses);
	}
}
