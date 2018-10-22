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
package com.cognifide.statistics.accelerator.foundation.datasource;

import static org.apache.sling.query.SlingQuery.$;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.query.SlingQuery;
import org.apache.sling.query.api.SearchStrategy;

public class SlingQueryImplicitStrategyCheck extends SlingAllMethodsServlet {

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
        throws ServletException, IOException {
        Resource resource = request.getResource();
        strategyNotDefined(resource);
        strategyDefined(resource);
    }

    private void strategyNotDefined(Resource resource) {
        SlingQuery sq = $(resource);
        sq.find(); // Noncompliant

        SlingQuery sq2 = $(resource).find("").find(); // Noncompliant

        SlingQuery.$(resource).find(""); // Noncompliant

        $(resource).find("res_type").asList().stream().map(r -> r.adaptTo(SlingQueryImplicitStrategyCheck.class).toString()).filter(StringUtils::isNotEmpty).iterator(); // Noncompliant
    }

    private void strategyDefined(Resource resource) {
        SlingQuery sq = $(resource).searchStrategy(SearchStrategy.DFS).find("");
        sq.find();

        SlingQuery sq2 = $(resource).searchStrategy(SearchStrategy.DFS).find("").find();

        SlingQuery.$(resource).searchStrategy(SearchStrategy.BFS).find("").find();

        $(resource).searchStrategy(SearchStrategy.QUERY).find("res_type").asList().stream()
            .map(r -> r.adaptTo(SlingQueryImplicitStrategyCheck.class).toString()).filter(StringUtils::isNotEmpty).iterator();
    }

    public void casesThatShouldBeIgnored(ResourceResolver resourceResolver, ScriptFinder scriptFinder) {
        Script script = scriptFinder.find(PERMISSION_SCRIPT_PATH, resourceResolver);

        while (matcher.find()) {
            String placeholder = matcher.group(REGEXP_PLACEHOLDER_KEY_GROUP);
        }
    }

    private GroupedSearchResponse groupedSearch(GroupedSearchQueryModel queryModel,
        GroupedSearchRepository repository) {
        log.info("Global search request [{}]", queryModel);
        if (StringUtils.EMPTY.equals(queryModel.getPreparedQuery())) {
            return prepareEmptyGroupedSearchResponse(queryModel);
        }
        AemRenditionsConfig renditionsConfig = queryModel.getRenditions();
        GroupPage<SolrDocument> groupPage =
            repository.find(queryModel.getSolrCore(),
                queryModel.getPreparedQuery(),
                queryModel.getGroupLimit(),
                queryModel.getSortType(),
                queryModel.getFiltersQueryModel().getFiltersCriteria());

        resources = resourceSelector.find(path, resolver);
    }

    @PostConstruct
    public void afterCreated() {
        this.ratingsAndReviews = Optional.of(resource.getResourceMetadata())
            .map(metadata -> metadata.get(ResourceMetadata.RESOLUTION_PATH))
            .filter(String.class::isInstance)
            .map(String::valueOf)
            .map(path -> resource.getResourceResolver().getResource(path))
            .map(r -> $(r).searchStrategy(SearchStrategy.QUERY).find(RATINGS_AND_REVIEWS_RESOURCE_TYPE).asList())
            .filter(CollectionUtils::isNotEmpty)
            .map(list -> list.get(0));
    }

    @PostConstruct
    public void afterCreated2() {
        this.ratingsAndReviews = Optional.of(resource.getResourceMetadata())
            .map(metadata -> metadata.get(ResourceMetadata.RESOLUTION_PATH))
            .filter(String.class::isInstance)
            .map(String::valueOf)
            .map(path -> resource.getResourceResolver().getResource(path))
            .map(r -> $(r).find(RATINGS_AND_REVIEWS_RESOURCE_TYPE).asList()) // Noncompliant
            .filter(CollectionUtils::isNotEmpty)
            .map(list -> list.get(0));
    }

    private void deleteProductPages(ResourceResolver resolver, String productPagesRootPath,
        String productsRootPath, boolean createFromScratch, List<String> logMessages) {
        String productPagesSql = StrSubstitutor.replace(FIND_PRODUCT_PAGES_QUERY, ImmutableMap
            .of(ROOT_PATH_PARAMETER, productPagesRootPath));
        Stream<Resource> productPages = StreamUtils
            .fromOrdered(resolver.findResources(productPagesSql, Query.JCR_SQL2))
            .sorted(Comparator.comparing(Resource::getPath).reversed());
        if (createFromScratch) {
            productPages.forEach(productPage -> deleteProductPage(resolver, productPage, logMessages));
        } else {
            productPages.forEach(
                productPage -> deleteProductPageIfProductNotExist(resolver, productPage, productsRootPath,
                    logMessages));
        }
    }

    private String setupApiKey(SlingHttpServletRequest request) {
        String result = StringUtils.EMPTY;
        Resource configurationResource = request.getResourceResolver().getResource(configurationPage);
        if (configurationResource != null) {
            Resource resource = Iterables.getFirst($(configurationResource).find(MapConfigurationModel.RESOURCE_TYPE), null); // Noncompliant
            result = Optional
                .ofNullable(resource)
                .map(configResource -> configResource.adaptTo(MapConfigurationModel.class))
                .map(MapConfigurationModel::getGoogleApiKey)
                .orElse(StringUtils.EMPTY);
        }
        if (StringUtils.isBlank(result)) {
            result = getFirstNotEmptyApiKey(request);
        }
        return result;
    }

    @PostConstruct
    public void afterCreated() {
        this.ratingsAndReviews = Optional.of(resource.getResourceMetadata())
            .map(metadata -> metadata.get(ResourceMetadata.RESOLUTION_PATH))
            .filter(String.class::isInstance)
            .map(String::valueOf)
            .map(path -> resource.getResourceResolver().getResource(path))
            .map(r -> test(r))
            .filter(CollectionUtils::isNotEmpty)
            .map(list -> list.get(0));

    }
    private List<Resource> test(Resource r) {
        return $(r).find(RATINGS_AND_REVIEWS_RESOURCE_TYPE).asList(); // Noncompliant

    }
}