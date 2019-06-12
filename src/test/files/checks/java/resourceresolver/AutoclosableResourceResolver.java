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
package com.example;

import java.util.Map;
import java.util.List;
import java.awt.Event;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

public class AutoclosableResourceResolver {

  @Reference
  private ResourceResolverFactory resourceResolverFactory;

  private ResourceResolver resolver;

  protected void resourceResolverInitialisedInTryBlockAndNotClosed() {
    ResourceResolver resourceResolver = null;
    try {
      resourceResolver = resourceResolverFactory.getServiceResourceResolver(null); // Noncompliant
    } catch (LoginException e) {
      System.out.println("something went wrong");
    } finally {
      if (resourceResolver != null) {
      }
    }
  }

  private void resourceResolverInitialisedInTryWithResourcesBlock() {
    try (ResourceResolver resourceResolver = resourceResolverFactory
        .getServiceResourceResolver(null)) {
      resourceResolver.getResource("path/to/resource");
    } catch (LoginException e) {
      System.out.println("something went wrong");
    }
  }

  public String resourceResolverInitialisedByResourceResolverProducerInTryBlockAndClosed(
      final String path) {
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

  public String resourceResolverDeclaredAndInitialisedInTryBlockAndClosed(final String path) {
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

  private void resourceResolverShouldBeIgnoredWhenResolverFactoryIsExecutedInMethodFromDifferentClass(
      Event event, List<String> attributesList) {
    if (attributesList.containsAll(ATTRIBUTES)) {
      String path = (String) event.getProperty(SlingConstants.PROPERTY_PATH);
      try {
        SlingHelper.operate(resolverFactory, resolver -> processPackage(resolver, path));
      } catch (OperateException e) {
        System.out.println("something went wrong");
      }
    }
  }
}