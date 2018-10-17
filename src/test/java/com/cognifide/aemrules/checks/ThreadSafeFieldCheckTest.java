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
package com.cognifide.aemrules.checks;

import org.junit.Test;

public class ThreadSafeFieldCheckTest extends AbstractBaseTest {

    @Test
    public void checkThreadSafeFieldsInServlet() {
        check = new ThreadSafeFieldCheck();
        filename = "src/test/files/checks/ThreadSafeFieldCheckServlet.java";
        verify();
    }

    @Test
    public void checkThreadSafeFieldsInFilter() {
        check = new ThreadSafeFieldCheck();
        filename = "src/test/files/checks/ThreadSafeFieldCheckFilter.java";
        verify();
    }

    @Test
    public void checkThreadSafeFieldsInEventHandler() {
        check = new ThreadSafeFieldCheck();
        filename = "src/test/files/checks/ThreadSafeFieldCheckEventHandler.java";
        verify();
    }

    @Test
    public void checkThreadSafeFieldsInScrComponent() {
        check = new ThreadSafeFieldCheck();
        filename = "src/test/files/checks/ThreadSafeFieldCheckScrComponentAnnotation.java";
        verify();
    }

    @Test
    public void checkThreadSafeFieldsInDsComponent() {
        check = new ThreadSafeFieldCheck();
        filename = "src/test/files/checks/ThreadSafeFieldCheckDsComponentAnnotation.java";
        verify();
    }

    @Test
    public void checkThreadSafeFieldsInSlingServletAnnotated() {
        check = new ThreadSafeFieldCheck();
        filename = "src/test/files/checks/ThreadSafeFieldCheckSlingServletAnnotation.java";
        verify();
    }
}
