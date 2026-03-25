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

// Pattern from wttech/.../src/test/files/java/ModifiableValueMapUsageCheck.java — getPropertyFromResource (ModifiableValueMapUsageCheckTest).

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;

public class ViolationAem17 {

  public Object getPropertyFromResource(Resource resource) {
    ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class);
    return createdResourceProperties.get("propName");
  }
}
