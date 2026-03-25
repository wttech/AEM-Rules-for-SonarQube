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

// Pattern from wttech/.../src/test/files/java/ThreadSafeFieldCheckServlet.java (ThreadSafeFieldCheckTest).

import com.day.cq.wcm.api.PageManager;

import javax.jcr.Session;
import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

@Component
@Service(value = javax.servlet.Servlet.class)
public class ViolationAem3 extends SlingSafeMethodsServlet {

  private static final class LayoutCache {}

  @Reference
  private LayoutCache layoutCache;

  private ResourceResolver resourceResolver;

  public ResourceResolver resourceResolver2;

  @Deprecated
  public static final ResourceResolver staticOne = null;

  private PageManager pageManager;

  private Session session;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
  }
}
