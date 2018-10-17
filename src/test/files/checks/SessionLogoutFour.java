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

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;

@Component(immediate = true)
public class SessionLogout {

    @Reference
    private SlingRepository repository;

    public void four() {
        Session session = null; // Noncompliant {{Session should be logged out in finally block.}}
        session = createAdminSession();
    }

    private Session createAdminSession() {
        Session result = null;
        try {
            result = repository.loginAdministrative(null);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return result;
    }

}