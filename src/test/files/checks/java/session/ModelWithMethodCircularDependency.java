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

import static org.apache.sling.query.SlingQuery.$;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.inject.Inject;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.query.api.SearchStrategy;

@SliceResource
public class ModelWithMethodCircularDependency implements InitializableModel {

    private static final String SOURCE_REFERENCE_TYPE = "sourceReference";

    private static final String DIFFERENT_SOURCE_REFERENCE_TYPE = "differentSourceReference";

    @Inject
    private Resource resource;

    @Inject
    private ResourceResolver resourceResolver;

    @Override
    public void afterCreated() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page page = pageManager.getContainingPage(resource);
        resource = page.getContentResource();
    }

    public String getPageDescription() {
        String description = resource.getValueMap().get("description", String.class);
        if (StringUtils.isBlank(description)) {
            description = getDescription(resource);
        }
        return description;
    }

    private String getDescription(Resource searchResource) {
        String text;
        List<Resource> resourceList = $(searchResource).searchStrategy(SearchStrategy.BFS)
            .find(DIFFERENT_SOURCE_REFERENCE_TYPE).asList();
        if (!resourceList.isEmpty()) {
            text = resourceList.get(0).getValueMap().get("description", String.class);
        } else {
            text = getDescriptionFromDifferentSource(searchResource);
        }
        return text;
    }

    private String getDescriptionFromDifferentSource(Resource resource) {
        String text = "";
        List<Resource> sourceList = $(resource)
            .searchStrategy(SearchStrategy.BFS)
            .find(SOURCE_REFERENCE_TYPE).asList();
        if (!sourceList.isEmpty()) {
            String path = sourceList.get(0).getValueMap().get("description", String.class);
            text = getDescription(resourceResolver.getResource(path)); // Noncompliant
        }
        return text;
    }

}
