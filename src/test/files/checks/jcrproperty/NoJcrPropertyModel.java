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

import com.cognifide.slice.mapper.annotation.SliceResource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

@SliceResource
public class NoJcrPropertyModel {

    private final String label;

    public NoJcrPropertyModel(final String path) {
        this.label = createLabel(path);
    }

    private String createLabel(final String path) {
        String result = StringUtils.substringAfterLast(path, "/");
        result = StringUtils.substringBeforeLast(result, ".");
        return WordUtils.capitalize(result);
    }

    public String getLabel() {
        return label;
    }

}
