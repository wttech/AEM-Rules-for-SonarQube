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

import java.util.Map;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;

public class ModifiableValueMapUsageCheck {

    public Resource setProperty(Resource resource) {
        ModifiableValueMap resourceProperties = resource.adaptTo(ModifiableValueMap.class);
        if (resourceProperties != null) {
            resourceProperties.put("test", resource.getName());
        }
        return resource;
    }

    public Resource removeProperty(Resource resource) {
        ModifiableValueMap resourcePropertiesToSet = resource.adaptTo(ModifiableValueMap.class);
        removePropertyFromMVM("testName", "testValue", resourcePropertiesToSet);
        return resource;
    }

    public Object getPropertyFromResource(Resource resource) {
        ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class); // Noncompliant ValueMap should be used
        return createdResourceProperties.get("propName");
    }

    public Object getProperty(Resource resource) {
        ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class); // Noncompliant ValueMap should be used
        return getPropertyFromValueMap("propName", createdResourceProperties);
    }

    private void removePropertyFromMVM(String propName, String propValue, Map<String, Object> mvm) {
        propName += "other";
        removeOtherPropertyFromMVM(mvm, propName, propValue);
    }

    private void removeOtherPropertyFromMVM(Map<String, Object> mvm2, String propName, String propValue) {
        mvm2.remove(propName, propValue);
    }

    private Object getPropertyFromValueMap(String propName, Map<String, Object> mvm) {
        mvm.getOrDefault(propName, "default");
    }

}