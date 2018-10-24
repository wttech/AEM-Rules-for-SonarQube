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

    private void firstCase(ResourceResolver resourceResolver, String path) {
        InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver); // Noncompliant {{Injector should be closed in finally block or created as a resource within try block.}}
        InjectorWithContext injector2 = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver); // Noncompliant
        ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
        injector2.close();
    }

    private void firstCase2(ResourceResolver resourceResolver, String path) {
        InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver); // Noncompliant
        InjectorWithContext injector2 = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver);
        try {
            ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
        } finally {
            injector2.close();
        }
    }

    private void secondCase(ResourceResolver resourceResolver) {
        InjectorWithContext injector = null; // Noncompliant
        injector = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver);
        ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
    }

    private void thirdCase(String path, ResourceResolver resourceResolver) {
        if (path.startsWith(":-)")) {
            InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver); // Noncompliant
            ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
        }
    }

    private void emptyMethod() {
    }

    public void fifthCase(SlingHttpServletRequest request) {
        InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, request);
        try {
            ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
        } finally {
            injector.close();
        }

        InjectorWithContext injector2 = InjectorUtil.getInjector(INJECTOR_NAME, request); // Noncompliant
        try {
            ModelProvider modelProvider = injector2.getInstance(ModelProvider.class);
        } finally {
            // sth
        }

        try (InjectorWithContext injector3 = InjectorUtil.getInjector(INJECTOR_NAME, request)) {
            ModelProvider modelProvider = injector3.getInstance(ModelProvider.class);
        }

        try (InjectorWithContext injector3a = InjectorUtil.getInjector(INJECTOR_NAME, request); InjectorWithContext injector3b = InjectorUtil
            .getInjector(INJECTOR_NAME, request)) {
            ModelProvider modelProvider = injector3a.getInstance(ModelProvider.class);
        }

        InjectorWithContext injector4 = InjectorUtil.getInjector(INJECTOR_NAME, request); // Noncompliant
        try {
            ModelProvider modelProvider = injector4.getInstance(ModelProvider.class);
            injector4.close();
        } catch (Exception e) {
            // sth
        }

        try {
            InjectorWithContext injector6 = InjectorUtil.getInjector(INJECTOR_NAME, request); // Noncompliant
            ModelProvider modelProvider = injector6.getInstance(ModelProvider.class);
            injector6.close();
        } finally {
            // sth
        }

        ResourceResolver resourceResolver = null;
        InjectorWithContext injector7 = null;
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

    private void someMethod(InjectorWithContext injector) {
        injector.getInstance(ModelProvider.class);
    }

}
