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

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SessionShouldBeLoggedOutTest extends AbstractBaseTest {

    // format is filename, failure expected
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"src/test/files/checks/SessionLogoutOne.java", false},
            {"src/test/files/checks/SessionLogoutTwo.java", false},
            {"src/test/files/checks/SessionLogoutThree.java", false},
            {"src/test/files/checks/SessionLogoutFour.java", true},
            {"src/test/files/checks/SessionLogoutFive.java", true},
            {"src/test/files/checks/SessionLogoutSix.java", true},
            {"src/test/files/checks/SessionLogoutSeven.java", true},
            {"src/test/files/checks/SessionLogoutEight.java", false},
            {"src/test/files/checks/LongSessionEventListener.java", false},
            {"src/test/files/checks/LongSessionEventListenerError.java", true}
        });
    }

    private boolean expectFailure;

    public SessionShouldBeLoggedOutTest(Object fn, Object expectFailure) {
        filename = (String) fn;
        this.expectFailure = ((Boolean) expectFailure).booleanValue();
    }

    @Test
    public void checkInjectorNotClosedInFinallyBlock() {
        check = new SessionShouldBeLoggedOut();
        if (expectFailure) {
            verify();
        } else {
            verifyNoIssues();
        }
    }

}
