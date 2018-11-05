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
package com.example;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.day.cq.wcm.api.Page;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class ContentResourceShouldBeNullCheckedCheck {

  private void contentResourceNotNullCheckedBeforeUsage(Resource resource) {
    Page page = resource.adaptTo(Page.class);
    Resource contentResourceA = page.getContentResource();
    Iterable<Resource> children = contentResourceA.getChildren(); // Noncompliant
  }

  private void notSafeUseOfResourceAfterIfWithNullCheck(Resource resource) {
    Page page = resource.adaptTo(Page.class);
    Resource contentResource = page.getContentResource("test");
    if (contentResource != null) {
      // do something
    }
    Iterable<Resource> children = contentResource.getChildren(); // Noncompliant
  }

  private void nullCheckUsedOnDifferentResourceThanCheckedInIfStatement(Resource resource1,
      Resource resource2) {
    Page page1 = resource1.adaptTo(Page.class);
    Page page2 = resource2.adaptTo(Page.class);
    Resource contentResource1 = page1.getContentResource();
    Resource contentResource2 = page2.getContentResource("test");
    if (contentResource1 != null) {
      Iterable<Resource> children = contentResource2.getChildren(); // Noncompliant
    }
  }

  private void correctlyNullCheckedContentResource(Resource resource1, Resource resource2) {
    Page page1 = resource1.adaptTo(Page.class);
    Page page2 = resource2.adaptTo(Page.class);
    Resource contentResource1 = page1.getContentResource();
    Resource contentResource2 = page2.getContentResource();
    Iterable<Resource> children = null;
    if (contentResource1 != null) {
      children = contentResource1.getChildren();
      if (contentResource2 != null) {
        children = contentResource1.getChildren();
      }
      children = contentResource1.getChildren();
    } else if (contentResource2 != null) {
      children = contentResource2.getChildren();
    }
  }

  private void directlyCallMethodOnGetContentResourceReturn(Resource resource) {
    Page page = resource.adaptTo(Page.class);
    ValueMap map = page.getContentResource().getValueMap(); // Noncompliant
    ModifiableValueMap modifiableValueMap = page.getContentResource().adaptTo(ModifiableValueMap.class); // Noncompliant
  }

  private void useOfOptionalInCaseOfNullContentResource(Resource resource) {
    Page page = resource.adaptTo(Page.class);
    Optional<Resource> contentResourceOptional = Optional.ofNullable(page.getContentResource());
    Iterable<Resource> children = contentResourceOptional.get().getChildren();
  }

  private boolean contentResourceNullCheckWithImmediateReturn(Resource resource) {
    Page page = resource.adaptTo(Page.class);
    Resource contentResource = page.getContentResource();
    if (contentResource == null) {
      return false;
    }
    contentResource.getValueMap();
    return true;
  }

  private ValueMap checkForNullWithObjectsNonNull(Resource resource) {
    ValueMap result = new ValueMapDecorator(new HashMap<>());
    Page page = resource.adaptTo(Page.class);
    Resource pageResource = page.getContentResource("test");
    if (Objects.nonNull(pageResource)) {
      result = pageResource.getValueMap();
    }
    return result;
  }

  private ValueMap checkForNullWithObjectsIsNull(Resource resource) {
    ValueMap result = new ValueMapDecorator(new HashMap<>());
    Page page = resource.adaptTo(Page.class);
    Resource pageResource = page.getContentResource("test");
    if (Objects.isNull(pageResource)) {
      return result;
    } else {
      return pageResource.getValueMap();
    }
  }

  private ValueMap checkForNullWithCommonsObjectUtilsAllNotNull(Resource resource) {
    ValueMap result = new ValueMapDecorator(new HashMap<>());
    Page page = resource.adaptTo(Page.class);
    Resource pageResource = page.getContentResource("test");
    if (ObjectUtils.allNotNull(pageResource)) {
      result = pageResource.getValueMap();
    }
    return result;
  }

  private ValueMap checkForNonNullWithCommonsObjectUtilsAllNotNullMultipleResources(Resource resource) {
    ValueMap result = new ValueMapDecorator(new HashMap<>());
    Page page = resource.adaptTo(Page.class);
    Resource pageResource = page.getContentResource("test");
    Resource pageResource2 = page.getContentResource("test");
    Resource pageResource3 = page.getContentResource("test");
    if (ObjectUtils.allNotNull(pageResource, pageResource2)) {
      result = pageResource.getValueMap();
      result = pageResource2.getValueMap();
      result = pageResource3.getValueMap(); // Noncompliant
    }
    return result;
  }
}
