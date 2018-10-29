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
package com.cognifide.statistics.accelerator.foundation.datasource;

import static org.apache.sling.query.SlingQuery.$;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.query.SlingQuery;
import org.apache.sling.query.api.SearchStrategy;
import org.apache.tools.ant.taskdefs.optional.Script;

public class SlingQueryImplicitStrategyCheck extends SlingAllMethodsServlet {

    private static final String SOMETHING = "something";

    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
        throws ServletException, IOException {
        Resource resource = request.getResource();
        strategyNotDefined(resource);
        strategyDefined(resource);
        casesThatShouldBeIgnored();
        testVer1();
        testVer2();
        test2();
    }

    private void strategyNotDefined(Resource resource) {
        SlingQuery sq = $(resource);
        sq.find(); // Noncompliant

        SlingQuery sq2 = $(resource).find("").find(); // Noncompliant

        SlingQuery.$(resource).find(""); // Noncompliant

        $(r).find("Something").asList(); // Noncompliant

        $(resource).find("res_type").asList().stream().map(r -> r.adaptTo(SlingQueryImplicitStrategyCheck.class).toString()); // Noncompliant
    }

    private void strategyDefined(Resource resource) {
        SlingQuery sq = $(resource).searchStrategy(SearchStrategy.DFS).find("");
        sq.find();

        SlingQuery sq2 = $(resource).searchStrategy(SearchStrategy.DFS).find("").find();

        SlingQuery.$(resource).searchStrategy(SearchStrategy.BFS).find("").find();

        $(resource).searchStrategy(SearchStrategy.QUERY).find("res_type").asList().stream()
            .map(r -> r.adaptTo(SlingQueryImplicitStrategyCheck.class).toString()).filter(StringUtils::isNotEmpty).iterator();
    }

    public void casesThatShouldBeIgnored() {
        Script script = scriptFinder.find(PERMISSION_SCRIPT_PATH, resourceResolver);

        while (matcher.find()) {
            String placeholder = matcher.group(REGEXP_PLACEHOLDER_KEY_GROUP);
        }

        GroupPage<SolrDocument> groupPage = repository.find(queryModel.getSolrCore(),
            queryModel.getPreparedQuery(),
            queryModel.getGroupLimit(),
            queryModel.getSortType(),
            queryModel.getFiltersQueryModel().getFiltersCriteria());
    }

    public void testVer1() {
        this.variable = Optional.of(resource.getResourceMetadata())
            .map(metadata -> metadata.get(ResourceMetadata.RESOLUTION_PATH))
            .filter(String.class::isInstance)
            .map(String::valueOf)
            .map(path -> resource.getResourceResolver().getResource(path))
            .map(r -> $(r).searchStrategy(SearchStrategy.QUERY).find(SOMETHING).asList())
            .map(list -> list.get(0));
    }

    public void testVer2() {
        this.variable = Optional.of(resource.getResourceMetadata())
            .map(metadata -> metadata.get(ResourceMetadata.RESOLUTION_PATH))
            .filter(String.class::isInstance)
            .map(String::valueOf)
            .map(path -> resource.getResourceResolver().getResource(path))
            .map(r -> $(r).find(SOMETHING).asList()) // Noncompliant
            .map(list -> list.get(0));
    }

    private Resource test2() {
        return resource = Iterables.getFirst($(configurationResource).find(MapConfigurationModel.RESOURCE_TYPE), null); // Noncompliant
    }
}