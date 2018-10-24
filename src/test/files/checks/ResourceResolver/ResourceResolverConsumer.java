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

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.process.AbstractAssetWorkflowProcess;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.metadata.MetaDataMap;
import javax.jcr.Session;
import org.apache.sling.api.resource.ResourceResolver;

public class ResourceResolverConsumer extends AbstractAssetWorkflowProcess {

    private final ResourceResolverProducer resourceResolverProducer;

    public ResourceResolverConsumer(ResourceResolverProducer resourceResolverProducer) {
        this.resourceResolverProducer = resourceResolverProducer;
    }

    public String findName(final String path) {
        String name = "";
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resourceResolverProducer.produce();
            name = resourceResolver.getResource(path).getName();
        } finally {
            if (null != resourceResolver && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
        return name;
    }

    public Tag findTag(String tagId, Asset asset, Session session) {
        Tag tag = null;
        ResourceResolver resourceResolver = null;

        try {
            resourceResolver = getResourceResolver(session);
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            tag = tagManager.resolve(tagId);
        } finally {
            if (null != resourceResolver && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }

        return tag;
    }

    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        // not used
    }
}
