/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2024 VML
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
package com.vml.aemrules.matcher;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodMatcherTest {

    public static final String UNIT_NAME = "test";
    private static final String TYPE_TEST_CLASS = "com.vml.test.TestClass";
    private static final String TYPE_MY_CLASS_1 = "com.vml.test.MyClass1";
    private static final String TYPE_MY_CLASS_2 = "com.vml.test.MyClass2";
    private static final String SAMPLE_IDENTIFIER = "src/test/files/matcher/MethodMatcherSampleIdentifier.java";
    private static final String SAMPLE_MEMBER_SELECT = "src/test/files/matcher/MethodMatcherSampleMemberSelect.java";

    private static final List<File> CLASSPATH_JAR;

    static {
        CLASSPATH_JAR = new ArrayList<>();
        String classPath = StringUtils.defaultIfBlank(System.getProperty("surefire.test.class.path"), System.getProperty("java.class.path"));
        if (StringUtils.isNotBlank(classPath)) {
            for (String jar : classPath.split(File.pathSeparator)) {
                if (jar.endsWith(".jar")) {
                    CLASSPATH_JAR.add(new File(jar));
                }
            }
        }
    }

    @Test
    void shouldMatchMethodWhenMethodNameAndOwnerClassAndMethodParametersMatch() {
        MethodMatcher methodMatcher = MethodMatcher.create(
                MethodNamePredicate.is(UNIT_NAME),
                OwnerTypePredicate.is(TYPE_TEST_CLASS),
                ParameterTypePredicate.is(TYPE_MY_CLASS_1),
                ParameterTypePredicate.is(TYPE_MY_CLASS_2)
        );

        assertMatcherOnSample(SAMPLE_IDENTIFIER, methodMatcher, true);
    }

    @Test
    void shouldMatchMethodWhenMethodNameAndOwnerClassAndOnlySecondParameterMatch() {
        MethodMatcher methodMatcher = MethodMatcher.create(
                MethodNamePredicate.is(UNIT_NAME),
                OwnerTypePredicate.is(TYPE_TEST_CLASS),
                ParameterTypePredicate.anyParameterType(),
                ParameterTypePredicate.is(TYPE_MY_CLASS_2)
        );

        assertMatcherOnSample(SAMPLE_MEMBER_SELECT, methodMatcher, true);
    }

    @Test
    void shouldNotMatchMethodWhenMethodNameDoesNotMatch() {
        MethodMatcher methodMatcher = MethodMatcher.create(
                MethodNamePredicate.is("different"),
                OwnerTypePredicate.is(TYPE_TEST_CLASS),
                ParameterTypePredicate.is(TYPE_MY_CLASS_1),
                ParameterTypePredicate.is(TYPE_MY_CLASS_2)
        );

        assertMatcherOnSample(SAMPLE_IDENTIFIER, methodMatcher, false);
    }

    @Test
    void shouldNotMatchMethodWhenNumberOfMethodParametersDoesNotMatch() {
        MethodMatcher methodMatcher = MethodMatcher.create(
                MethodNamePredicate.is(UNIT_NAME),
                OwnerTypePredicate.is(TYPE_TEST_CLASS),
                ParameterTypePredicate.is(TYPE_MY_CLASS_1)
        );

        assertMatcherOnSample(SAMPLE_IDENTIFIER, methodMatcher, false);
    }

    @Test
    void shouldNotMatchMethodWhenMethodOwnerClassDoesNotMatch() {
        MethodMatcher methodMatcher = MethodMatcher.create(
                MethodNamePredicate.is(UNIT_NAME),
                OwnerTypePredicate.is("com.vml.test.Different"),
                ParameterTypePredicate.is(TYPE_MY_CLASS_1),
                ParameterTypePredicate.is(TYPE_MY_CLASS_2)
        );

        assertMatcherOnSample(SAMPLE_IDENTIFIER, methodMatcher, false);
    }

    @Test
    void shouldNotMatchMethodWhenMethodParameterTypesDoNotMatch() {
        MethodMatcher methodMatcher = MethodMatcher.create(
                MethodNamePredicate.is(UNIT_NAME),
                OwnerTypePredicate.is(TYPE_TEST_CLASS),
                ParameterTypePredicate.is("com.vml.test.Different1"),
                ParameterTypePredicate.is(TYPE_MY_CLASS_2)
        );

        assertMatcherOnSample(SAMPLE_MEMBER_SELECT, methodMatcher, false);
    }

    private static void assertMatcherOnSample(String relativePath, final MethodMatcher matcher, final boolean expected) {
        class MatcherProbe extends BaseTreeVisitor implements JavaFileScanner {
            @Override
            public void scanFile(JavaFileScannerContext context) {
                scan(context.getTree());
            }

            @Override
            public void visitMethodInvocation(MethodInvocationTree tree) {
                assertThat(matcher.matches(tree), is(expected));
            }
        }

        CheckVerifier.newVerifier()
                .onFile(relativePath)
                .withClassPath(CLASSPATH_JAR)
                .withCheck(new MatcherProbe())
                .verifyNoIssues();
    }
}
