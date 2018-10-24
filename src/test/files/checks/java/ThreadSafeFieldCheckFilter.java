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

import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.PageManager;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;

@SlingFilter(scope = SlingFilterScope.COMPONENT, order = Integer.MAX_VALUE, metatype = true, label =
    "label", description = "description")
public class ThreadSafeFieldCheckFilterExample implements Filter {

    private PageManager pageManager; // Noncompliant {{Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.}}

    private TagManager tagManager; // Noncompliant

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // init
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
        throws IOException, ServletException {
        // filter
    }

    @Override
    public void destroy() {
        // nothing to do
    }

}
