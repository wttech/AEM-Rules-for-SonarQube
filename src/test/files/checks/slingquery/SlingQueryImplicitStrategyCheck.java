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

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.query.SlingQuery;
import org.apache.sling.query.api.SearchStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.sling.query.SlingQuery.$;

public class SlingQueryImplicitStrategyCheck extends SlingAllMethodsServlet {

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		Resource resource = request.getResource();
		// static import, splitted implicit
		SlingQuery sq = $(resource);
		sq.find(); // Noncompliant
		// static import, splitted explicit
		SlingQuery sq2 = $(resource).searchStrategy(SearchStrategy.DFS).find("");
		sq2.find();
		// static import, implicit
		SlingQuery sq3 = $(resource).find("").find(); // Noncompliant
		// static import, explicit
		SlingQuery sq4 = $(resource).searchStrategy(SearchStrategy.DFS).find("").find();
		// implicit
		SlingQuery.$(resource).find(""); // Noncompliant
		// explicit
		SlingQuery.$(resource).searchStrategy(SearchStrategy.BFS).find("").find();
		$(resource).searchStrategy(SearchStrategy.QUERY)
				.find("res_type").asList().stream()
				.map(r -> r.adaptTo(SlingQueryImplicitStrategyCheck.class).toString())
				.filter(StringUtils::isNotEmpty).iterator();

		$(resource).find("res_type").asList().stream() // Noncompliant
				.map(r -> r.adaptTo(SlingQueryImplicitStrategyCheck.class).toString())
				.filter(StringUtils::isNotEmpty).iterator();
	}
}
