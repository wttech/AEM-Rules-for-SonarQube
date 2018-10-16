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
import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * @author Krzysztof Watral
 */
@SliceResource
public class SampleModel implements InitializableModel {

    private final ResourceResolver resolver;

    private ResourceResolver resolver2;

    @JcrProperty
    private String prop;

    public SampleModel(ResourceResolver resolver) {
        this.resolver = resolver;
    }

    public String getProp() {
        return prop;
    }

    public String getLowerCaseProp() {
        return prop.toLowerCase();
    }

    public String getPropRR() {
        resolver.getAttribute("TEST"); // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().}}
        StringUtils.isBlank((String) resolver2.getAttribute("TEST")); // Noncompliant
        return "null";
    }

    public ResourceResolver getResolver() {
        return resolver; // Noncompliant
    }

    public String getNothing() {
        return null;
    }

    @Override
    public void afterCreated() {
        resolver.getAttribute("TEST");
    }
}