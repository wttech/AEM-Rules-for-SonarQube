/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Wunderman Thompson Technology
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
package com.cognifide.aemrules.java.checks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.plugins.java.api.JavaFileScanner;

public abstract class AbstractBaseTest {

    /**
     * JAR dependencies for classpath execution
     */
    private static final List<File> CLASSPATH_JAR;

    /**
     * When run from maven.
     */
    private static final String SUREFIRE_TEST_CLASS_PATH = "surefire.test.class.path";

    /**
     * When run from IDE.
     */
    private static final String JAVA_CLASS_PATH = "java.class.path";

    static {
        CLASSPATH_JAR = new ArrayList<>();
        String classPath = StringUtils.defaultIfBlank(System.getProperty(SUREFIRE_TEST_CLASS_PATH), System.getProperty(JAVA_CLASS_PATH));
        if (StringUtils.isNotBlank(classPath)) {
            for (String jar : classPath.split(File.pathSeparator)) {
                if (jar.endsWith(".jar")) {
                    CLASSPATH_JAR.add(new File(jar));
                }
            }
        }
    }

    protected JavaFileScanner check;

    protected String filename;

    protected void verify() {
        verify(true);
    }

    protected void verify(boolean withJarClassPath) {
        if (withJarClassPath) {
            CheckVerifier.newVerifier()
                    .onFile(filename)
                    .withCheck(check)
                    .withClassPath(CLASSPATH_JAR)
                    .verifyIssues();
        } else {
            CheckVerifier.newVerifier()
                    .onFile(filename)
                    .withCheck(check)
                    .verifyIssues();
        }
    }

    protected void verifyNoIssues() {
        CheckVerifier.newVerifier()
                .onFile(filename)
                .withCheck(check)
                .verifyNoIssues();
    }
}
