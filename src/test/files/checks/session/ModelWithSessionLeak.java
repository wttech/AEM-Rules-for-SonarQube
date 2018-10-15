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

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.mapper.annotation.SliceResource;
import org.apache.sling.api.resource.ResourceResolver;

@SliceResource
public class ModelWithSessionLeak extends ParameterAwareModel implements InitializableModel {

    private final ResourceResolver resolver;

    private Object attribute;

    public ModelWithSessionLeak(ResourceResolver resolver) {
        this.resolver = resolver;
        updateAttribute(resolver);
    }

    public ResourceResolver getResolver() {
        return resolver; // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().}}
    }

    public Object getAttribute() {
        updateAttribute3(resolver);
        update(resolver);
        return attribute;
    }

    public ResourceResolver getResourceResolver() {
        String parameter = findParameter(resolver);
        return null;
    }

    @Override
    public void afterCreated() {
        attribute = resolver.getAttribute("attribute");
        updateAttribute3(null);
    }

    private void update(ResourceResolver resolver) {
        if (true) {
            updateAttribute2(resolver);
        }
    }

    private void updateAttribute3(ResourceResolver resolver) {
        attribute = resolver.getAttribute("attribute"); // Noncompliant
        attribute = this.resolver.getAttribute("attribute"); // Noncompliant
    }

    private void updateAttribute2(ResourceResolver resolver) {
        attribute = resolver.getAttribute("attribute"); // Noncompliant
        attribute = this.resolver.getAttribute("attribute"); // Noncompliant
    }

    private void updateAttribute(ResourceResolver resolver) {
        attribute = resolver.getAttribute("attribute");
        attribute = this.resolver.getAttribute("attribute");
        updateAttribute2(resolver);
    }

}
