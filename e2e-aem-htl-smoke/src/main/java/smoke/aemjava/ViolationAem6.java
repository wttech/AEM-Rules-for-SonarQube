/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2024 VML
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
package smoke.aemjava;

// Pattern from wttech/.../src/test/files/java/SampleServlet.java — doGet (ResourceResolverShouldBeClosedTest).

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(paths = "smoke/aem6", methods = {HttpConstants.METHOD_GET})
public class ViolationAem6 extends SlingAllMethodsServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(ViolationAem6.class);

  @Reference
  private ResourceResolverFactory resourceResolverFactory;

  @Override
  protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
      throws ServletException, IOException {
    ResourceResolver resourceResolver = null;
    try {
      resourceResolver = resourceResolverFactory.getResourceResolver(null);
    } catch (LoginException e) {
      LOGGER.error("Error during getting instance of ResourceResolver class", e);
    } finally {
      if (resourceResolver != null) {
        // intentionally not closed (same as unit test)
      }
    }
  }
}
