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

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import java.util.Properties;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;

/**
 * @author Krzysztof Watral
 */
@SliceResource
public class JcrPropertySampleModel {

    @JcrProperty
    private String name;

    @JcrProperty
    private String surname;

    private String modName;

    private String city;

    public JcrPropertySampleModel() {
    }

    public JcrPropertySampleModel(int i) {
        if (StringUtils.isBlank(name)) { // Noncompliant {{Fields annotated by @JcrProperty shouldn't be accessed from constructor.}}
            modName = surname + i; // Noncompliant
        }
        city = "Poznan";
    }

    @Inject
    public JcrPropertySampleModel(Properties properties) {
        city = properties.getProperty("city");
    }

    public JcrPropertySampleModel(String surname) {
        this.surname = surname; // Noncompliant
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
