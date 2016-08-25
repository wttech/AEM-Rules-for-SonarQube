package com.example;

import com.cognifide.slice.api.injector.InjectorWithContext;
import com.cognifide.slice.api.provider.ModelProvider;
import com.cognifide.slice.util.InjectorUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

public class ClosingInjector {

	public static final String INJECTOR_NAME = "injector_name";

	public ClosingInjector() {
		super();
	}

	public void fifthCase(SlingHttpServletRequest request) {
		InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, request); // Noncompliant
		try {
			ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
		} finally {
			injector.close();
		}

		try (InjectorWithContext injector3 = InjectorUtil.getInjector(INJECTOR_NAME, request)) {
			ModelProvider modelProvider = injector3.getInstance(ModelProvider.class);
		}

		try (InjectorWithContext injector3a = InjectorUtil.getInjector(INJECTOR_NAME, request); InjectorWithContext injector3b = InjectorUtil.getInjector(INJECTOR_NAME, request)) {
			ModelProvider modelProvider = injector3a.getInstance(ModelProvider.class);
		}

		try {
			InjectorWithContext injector6 = InjectorUtil.getInjector(INJECTOR_NAME, request); // Noncompliant
			ModelProvider modelProvider = injector6.getInstance(ModelProvider.class);
			injector6.close();
		} finally {
			// sth
		}

		ResourceResolver resourceResolver = null;
		InjectorWithContext injector7 = null; // Noncompliant
		try {
			injector7 = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver);
		} catch (Exception e) {
			// sth
		} finally {
			if (injector7 != null) {
				injector7.close();
			}
		}
	}

}
