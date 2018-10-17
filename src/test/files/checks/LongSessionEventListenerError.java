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
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;

@Component
public class LongSessionEventListenerError implements EventListener {

    private Session observationSession; // Noncompliant {{Session should be logged out in finally block.}}

    @Reference
    private SlingRepository repository;

    @Activate
    public void activate(final Map<String, String> config) throws RepositoryException {
        observationSession = repository.loginAdministrative(null);
        final ObservationManager observationManager = observationSession.getWorkspace().getObservationManager();
    }

    @Deactivate
    public void deactivate(final Map<String, String> config) throws RepositoryException {
        final ObservationManager observationManager = observationSession.getWorkspace().getObservationManager();

        if (observationManager != null) {
            observationManager.removeEventListener(this);
        }
    }

    @Override
    public void onEvent(final EventIterator events) {
    }
}