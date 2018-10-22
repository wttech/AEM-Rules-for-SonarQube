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
package com.cognifide.aemrules.checks.slice.iterator;

import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.HashSet;
import java.util.Set;

import static com.cognifide.aemrules.Constants.RESOURCE_TYPE;

class MethodInvocationTreeVisitor extends BaseTreeVisitor {

    private static final MethodMatcher MODEL_PROVIDER_GET_MATCHER = MethodMatcher
        .create()
        .typeDefinition("com.cognifide.slice.api.provider.ModelProvider")
        .name("get")
        .addParameter(TypeCriteria.anyType())
        .addParameter(RESOURCE_TYPE);

    private final Set<MethodInvocationTree> visitedMethodInvocationTrees;

    private boolean modelProviderGetCalled;

    MethodInvocationTreeVisitor() {
        this(new HashSet<MethodInvocationTree>(16));
    }

    MethodInvocationTreeVisitor(Set<MethodInvocationTree> visitedMethodInvocationTrees) {
        this.visitedMethodInvocationTrees = new HashSet<>(visitedMethodInvocationTrees);
    }

    public boolean isModelProviderGetCalled() {
        return modelProviderGetCalled;
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (wasNotVisitedYet(tree)) {
            if (MODEL_PROVIDER_GET_MATCHER.matches(tree)) {
                modelProviderGetCalled = true;
            } else {
                MethodTree methodDeclaration = getMethodTree(tree);
                if (methodDeclaration != null) {
                    MethodInvocationTreeVisitor visitor = new MethodInvocationTreeVisitor(visitedMethodInvocationTrees);
                    methodDeclaration.accept(visitor);
                    modelProviderGetCalled = visitor.isModelProviderGetCalled();
                } else {
                    super.visitMethodInvocation(tree);
                }
            }
        }
    }

    private boolean wasNotVisitedYet(MethodInvocationTree tree) {
        boolean wasNotVisited = !visitedMethodInvocationTrees.contains(tree);
        if (wasNotVisited) {
            visitedMethodInvocationTrees.add(tree);
        }
        return wasNotVisited;
    }

    private MethodTree getMethodTree(MethodInvocationTree methodInvocation) {
        if (methodInvocation.methodSelect() instanceof IdentifierTree) {
            IdentifierTree method = (IdentifierTree) methodInvocation.methodSelect();
            return (MethodTree) getDeclaration(method);
        }
        return null;
    }

    private Tree getDeclaration(IdentifierTree variable) {
        return variable.symbol().declaration();
    }

}
