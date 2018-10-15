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

import com.day.cq.wcm.api.Page;
import java.util.Optional;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class ContentResourceCheckB {

  private Page pageA;

  private Page pageB;

  private Page pageC;

  private void noNullCheck() {
    Resource contentResourceA = pageA.getContentResource();
    Resource contentResourceB = pageB.getContentResource();
    ValueMap map = pageC.getContentResource().getValueMap(); // Noncompliant
    Iterable<Resource> children = contentResourceA.getChildren(); // Noncompliant
    Resource pageContentResource = pageA.getContentResource();
    ModifiableValueMap pageProperties = pageContentResource.adaptTo(ModifiableValueMap.class); // Noncompliant
    if (contentResourceA != null) {
      Iterable<Resource> childrenB = contentResourceB.getChildren(); // Noncompliant
    }
    Iterable<Resource> childrenB = contentResourceA.getChildren(); // Noncompliant
  }

  private void withNullCheck() {
    Resource contentResourceA = pageA.getContentResource();
    Resource contentResourceB = pageB.getContentResource();
    Iterable<Resource> children = null;
    if (contentResourceA != null) {
      children = contentResourceA.getChildren();
      children = contentResourceB.getChildren(); // Noncompliant
      if(contentResourceB != null) {
        children = contentResourceA.getChildren();
      }
      children = contentResourceA.getChildren();
    } else if (contentResourceB != null) {
      children = contentResourceB.getChildren();
    }
  }

  private void noNullCheckNeeded() {
    Optional<Resource> contentResourceO = Optional.ofNullable(page.getContentResource());
    Iterable<Resource> children = contentResourceO.get().getChildren();
  }

  private boolean theOtherNullCheck() {
    Resource contentResource = pageA.getContentResource();
    if (contentResource == null) {
      return false;
    }
    contentResource.getValueMap();
    return true;
  }
}