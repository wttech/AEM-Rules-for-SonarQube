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

class Constants {

    public static final String UNIQUE_CONSTANT = "limited in occurrence to a given class, situation, or area:";
    public static final String JCR_CREATED = "jcr:created"; // Noncompliant {{Use constant JCR_CREATED from interface com.day.cq.commons.jcr.JcrConstants or constant PN_CREATED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition instead of hardcoded value.}}
    public static final String NT_FOLDER = "nt:folder"; // Noncompliant {{Use constant NT_FOLDER from interface com.day.cq.commons.jcr.JcrConstants instead of hardcoded value.}}
    public static final String NT_DAM_ASSET;

    static {
        NT_DAM_ASSET = "dam:Asset"; // Noncompliant {{Use constant NT_DAM_ASSET from interface com.day.cq.dam.api.DamConstants instead of hardcoded value.}}
    }

    public static String getConstant() {
        return "nt:resource"; // Noncompliant {{Use constant NT_RESOURCE from interface com.day.cq.commons.jcr.JcrConstants instead of hardcoded value.}}
    }
}