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

import java.util.Map;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

public class AutoclosableResourceResolver {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private ResourceResolver resolver;

    protected void case1() {
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(null); // Noncompliant
        } catch (LoginException e) {
            System.out.println("something went wrong");
        } finally {
            if (resourceResolver != null) {
                //resourceResolver.close();
            }
        }
    }

    private void case2() {
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(null)) {
            resourceResolver.getResource("path/to/resource");
        } catch (LoginException e) {
            System.out.println("something went wrong");
        }
    }

    public String case3(final String path) {
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resourceResolverProducer.produce(); // Noncompliant
            name = resourceResolver.getResource(path).getName();
        } finally {
            if (null != resourceResolver && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
        return name;
    }

    public String case4(final String path) {
        ResourceResolver resourceResolver = null;
        try {
            String name = resourceResolver.getResource(path).getName();
            ResourceResolver resourceResolver = resourceResolverProducer.produce(); // Noncompliant
        } finally {
            if (null != resourceResolver && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
        return name;
    }

    protected void case5(final Map<String, Object> properties) {
        try {
            resolver = resourceResolverFactory.getAdministrativeResourceResolver(null); // Noncompliant
            registerObservation();
        } catch (LoginException x) {
        }
    }
}
