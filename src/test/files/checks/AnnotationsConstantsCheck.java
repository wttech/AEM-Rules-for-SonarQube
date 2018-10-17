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

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

@SlingServlet(
    resourceTypes = {"some/resource/path"},
    selectors = {"inspect", "install"},
    extensions = {"json"},
    methods = {"POST"} // Noncompliant {{Use constant METHOD_POST from class org.apache.sling.api.servlets.HttpConstants instead of hardcoded value.}}
)
@Properties({
    @Property(name = "service.vendor", value = ZenGardenConstants.VENDOR_NAME), // Noncompliant {{Use constant SERVICE_VENDOR from interface org.osgi.framework.Constants instead of hardcoded value.}}
    @Property(name = "service.description", value = "Provides import process.") // Noncompliant {{Use constant SERVICE_DESCRIPTION from interface org.osgi.framework.Constants instead of hardcoded value.}}
})
public class AnnotationsConstantsCheck extends SlingAllMethodsServlet {

    private static final String ID_PARAM = "service.description";

    @Override("Test")
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {
    }

    @Override("service.description") // Noncompliant {{Use constant SERVICE_DESCRIPTION from interface org.osgi.framework.Constants instead of hardcoded value.}}
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {
    }

}
