/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2018 Cognifide Limited
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
package com.cognifide.aemrules.java.checks.slice.session;

import java.util.HashSet;
import java.util.Set;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

class MethodInvocationVisitor extends BaseTreeVisitor {

    private final JavaFileScanner javaFileScanner;

    private final JavaFileScannerContext context;

    private final Set<MethodInvocationTree> visitedMethodInvocationTrees;

    MethodInvocationVisitor(JavaFileScanner javaFileScanner, JavaFileScannerContext context) {
        this.javaFileScanner = javaFileScanner;
        this.context = context;
        this.visitedMethodInvocationTrees = new HashSet<>();
    }

    MethodInvocationVisitor(JavaFileScanner javaFileScanner, JavaFileScannerContext context, Set<MethodInvocationTree> visitedMethodInvocationTrees) {
        this.javaFileScanner = javaFileScanner;
        this.context = context;
        this.visitedMethodInvocationTrees = new HashSet<>(visitedMethodInvocationTrees);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        tree.accept(new SessionUsageVisitor(javaFileScanner, context));
        super.visitMethod(tree);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        Tree declaration = tree.symbol().declaration();
        if (null != declaration && !visitedMethodInvocationTrees.contains(tree)) {
            visitedMethodInvocationTrees.add(tree);
            declaration.accept(new MethodInvocationVisitor(javaFileScanner, context, visitedMethodInvocationTrees));
        }
        super.visitMethodInvocation(tree);
    }

}
