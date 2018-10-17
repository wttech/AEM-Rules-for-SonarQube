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

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SlingServlet(paths = "somePath", methods = {HttpConstants.METHOD_GET})
public class SampleServlet extends SlingAllMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleServlet.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
        throws ServletException, IOException {
        ResourceResolver resourceResolver = null; // Noncompliant {{ResourceResolver should be closed in finally block.}}
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);
        } catch (LoginException e) {
            LOGGER.error("Error during getting instance of ResourceResolver class", e);
        } finally {
            if (resourceResolver != null) {
                //resourceResolver.close();
            }
        }
    }

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
        throws ServletException, IOException {
        ResourceResolver resourceResolver = null;
        resourceResolver = request.getResourceResolver();
        // do sth
    }

    /**
     * Create a new session for specified user (impersonating).
     */
    public static ResourceResolver getResourceResolverForUser(ResourceResolverFactory factory, String userId)
        throws LoginException {
        ResourceResolver resolver;
        if (userId != null) {
            Map<String, Object> authenticationInfo = Maps.newHashMap();
            authenticationInfo.put(ResourceResolverFactory.USER_IMPERSONATION, userId);
            resolver = factory.getServiceResourceResolver(authenticationInfo);
        } else {
            resolver = factory.getServiceResourceResolver(null);
        }
        return resolver;
    }

    public static ResourceResolver getResourceResolverForUserNested(ResourceResolverFactory factory, String userId)
        throws LoginException {
        ResourceResolver resolver;
        resolver = getResourceResolverForUser(resourceResolverFactory, userId);
        return resolver;
    }

    public static ResourceResolver getResourceResolverForUserInit(ResourceResolverFactory factory)
        throws LoginException {
        ResourceResolver resolver = factory.getServiceResourceResolver(null);
        return resolver;
    }

    public static ResourceResolver getResourceResolverForUserNestedInit(ResourceResolverFactory factory)
        throws LoginException {
        ResourceResolver resolver = getResourceResolverForUserInit(resourceResolverFactory);
        return resolver;
    }

    public void checkCorrectJumpMethod() {
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = getResourceResolverForUserNested(resourceResolverFactory, null);
        } catch (LoginException e) {
            e.printStackTrace();
        } finally {
            if (resourceResolver != null) {
                resourceResolver.close();
            }
        }
    }

    public void checkWrongJumpMethod() {
        ResourceResolver resourceResolver = null; // Noncompliant
        try {
            resourceResolver = getResourceResolverForUserNestedInit(resourceResolverFactory);
        } catch (LoginException e) {
            e.printStackTrace();
        } finally {
            if (resourceResolver != null) {
                //resourceResolver.close();
            }
        }
    }

}
