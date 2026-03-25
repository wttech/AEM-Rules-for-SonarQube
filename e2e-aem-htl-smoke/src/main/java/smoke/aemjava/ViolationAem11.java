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

// Pattern from wttech/.../src/test/files/java/AdministrativeAccessUsageCheck.java (AdministrativeAccessUsageCheckTest).

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Map;

@Component
public class ViolationAem11 {

  @Reference
  private ResourceResolverFactory resolverFactory;

  @Reference
  private SlingRepository repository;

  public ResourceResolver getResourceResolver(Map<String, Object> credentials) {
    ResourceResolver resolver = null;
    try {
      resolver = resolverFactory.getAdministrativeResourceResolver(credentials);
    } catch (LoginException e) {
      //
    }
    return resolver;
  }

  public Session getSession(String workspace) {
    Session session = null;
    try {
      session = repository.loginAdministrative(workspace);
    } catch (RepositoryException e) {
      //
    }
    return session;
  }
}
