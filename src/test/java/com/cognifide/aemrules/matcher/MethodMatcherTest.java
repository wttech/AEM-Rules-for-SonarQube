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
package com.cognifide.aemrules.matcher;

import static org.hamcrest.CoreMatchers.is;

import com.sonar.sslr.api.typed.ActionParser;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.sonar.java.ast.parser.JavaParser;
import org.sonar.java.bytecode.loader.SquidClassLoader;
import org.sonar.java.resolve.SemanticModel;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;

public class MethodMatcherTest {

    private static final ActionParser<Tree> JAVA_PARSER = JavaParser.createParser();

    private static final String CODE_TO_PARSE_METHOD_SELECT_KIND_IDENTIFIER = "package com.cognifide.test; "
        + "class MyClass1 {}"
        + "class MyClass2 {}"
        + "class TestClass { "
        + "void test(MyClass1 m1, MyClass2 m2){}"
        + "void bar(){MyClass1 m1 = new MyClass1(); MyClass2 m2 = new MyClass2(); test(m1, m2);} "
        + "}";

    private static final String CODE_TO_PARSE_METHOD_SELECT_KIND_MEMBER_SELECT = "package com.cognifide.test; "
        + "class MyClass1 {}"
        + "class MyClass2 {}"
        + "class TestClass { "
        + "void test(MyClass1 m1, MyClass2 m2){}"
        + "void bar(){MyClass1 m1 = new MyClass1(); MyClass2 m2 = new MyClass2(); this.test(m1, m2);} "
        + "}";

    private static final int CLASS_INDEX = 2;

    private static final int CLASS_METHOD_INDEX = 1;

    private static final int METHOD_INVOCATION_INDEX = 2;

    private MethodInvocationTree methodInvocationTree;

    @Test
    public void shouldMatchMethodWhenMethodNameOwnerClassAndMethodParametersAgree() {
        givenMethodInvocationTree(CODE_TO_PARSE_METHOD_SELECT_KIND_IDENTIFIER);

        MethodMatcher methodMatcher = MethodMatcher.create(
            MethodNamePredicate.is("test"),
            TypePredicate.is("com.cognifide.test.TestClass"),
            TypePredicate.is("com.cognifide.test.MyClass1"),
            TypePredicate.is("com.cognifide.test.MyClass2")
        );

        Assert.assertThat(methodMatcher.matches(methodInvocationTree), is(true));
    }

    @Test
    public void shouldMatchMethodWhenMethodNameOwnerClassAndOneParameterAnyAndSecondStrict() {
        givenMethodInvocationTree(CODE_TO_PARSE_METHOD_SELECT_KIND_MEMBER_SELECT);

        MethodMatcher methodMatcher = MethodMatcher.create(
            MethodNamePredicate.is("test"),
            TypePredicate.is("com.cognifide.test.TestClass"),
            TypePredicate.anyType(),
            TypePredicate.is("com.cognifide.test.MyClass2")
        );

        Assert.assertThat(methodMatcher.matches(methodInvocationTree), is(true));
    }

    @Test
    public void shouldNotMatchMethodWhenMethodNameDifferent() {
        givenMethodInvocationTree(CODE_TO_PARSE_METHOD_SELECT_KIND_IDENTIFIER);

        MethodMatcher methodMatcher = MethodMatcher.create(
            MethodNamePredicate.is("different"),
            TypePredicate.is("com.cognifide.test.TestClass"),
            TypePredicate.is("com.cognifide.test.MyClass1"),
            TypePredicate.is("com.cognifide.test.MyClass2")
        );

        Assert.assertThat(methodMatcher.matches(methodInvocationTree), is(false));
    }

    @Test
    public void shouldNotMatchMethodWhenNumberOfMethodParametersDifferent() {
        givenMethodInvocationTree(CODE_TO_PARSE_METHOD_SELECT_KIND_MEMBER_SELECT);

        MethodMatcher methodMatcher = MethodMatcher.create(
            MethodNamePredicate.is("test"),
            TypePredicate.is("com.cognifide.test.TestClass"),
            TypePredicate.is("com.cognifide.test.MyClass1")
        );

        Assert.assertThat(methodMatcher.matches(methodInvocationTree), is(false));
    }

    @Test
    public void shouldNotMatchMethodWhenMethodOwnerClassDifferent() {
        givenMethodInvocationTree(CODE_TO_PARSE_METHOD_SELECT_KIND_IDENTIFIER);

        MethodMatcher methodMatcher = MethodMatcher.create(
            MethodNamePredicate.is("test"),
            TypePredicate.is("com.cognifide.test.Different"),
            TypePredicate.is("com.cognifide.test.MyClass1"),
            TypePredicate.is("com.cognifide.test.MyClass2")
        );

        Assert.assertThat(methodMatcher.matches(methodInvocationTree), is(false));
    }

    @Test
    public void shouldNotMatchMethodWhenMethodParametersDifferent() {
        givenMethodInvocationTree(CODE_TO_PARSE_METHOD_SELECT_KIND_MEMBER_SELECT);

        MethodMatcher methodMatcher = MethodMatcher.create(
            MethodNamePredicate.is("test"),
            TypePredicate.is("com.cognifide.test.TestClass"),
            TypePredicate.is("com.cognifide.test.Different1"),
            TypePredicate.is("com.cognifide.test.MyClass2")
        );

        Assert.assertThat(methodMatcher.matches(methodInvocationTree), is(false));
    }

    private void givenMethodInvocationTree(String codeToParse) {
        CompilationUnitTree compilationUnitTree = (CompilationUnitTree) JAVA_PARSER.parse(codeToParse);
        SemanticModel.createFor(compilationUnitTree, new SquidClassLoader(Collections.emptyList()));
        ClassTree classTree = (ClassTree) compilationUnitTree.types().get(CLASS_INDEX);
        StatementTree statementTree = ((MethodTree) classTree.members().get(CLASS_METHOD_INDEX)).block().body().get(METHOD_INVOCATION_INDEX);
        this.methodInvocationTree = (MethodInvocationTree) ((ExpressionStatementTree) statementTree).expression();
    }

}

