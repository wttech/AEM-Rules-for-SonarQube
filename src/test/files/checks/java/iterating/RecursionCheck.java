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

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;

public class RecursionCheck {

    public void processResource(Resource resource) {
        if (isGreen(resource) || isRed(resource)) {
            Iterator<Resource> iterator = resource.listChildren();
            while (iterator.hasNext()) {
                processResource(iterator.next());
            }
        }
    }

    private boolean isGreen(Resource resource) {
        return StringUtils.equals("green", resource.getValueMap().get("colour", String.class));
    }

    private boolean isRed(Resource resource) {
        return StringUtils.equals("red", resource.getValueMap().get("colour", String.class));
    }
}
