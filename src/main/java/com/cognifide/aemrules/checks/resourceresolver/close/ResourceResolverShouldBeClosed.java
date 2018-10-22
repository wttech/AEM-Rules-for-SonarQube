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
package com.cognifide.aemrules.checks.resourceresolver.close;

import com.cognifide.aemrules.checks.visitors.CheckClosedVisitor;
import com.cognifide.aemrules.checks.visitors.FinallyBlockVisitor;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = ResourceResolverShouldBeClosed.RULE_KEY,
    name = ResourceResolverShouldBeClosed.RULE_MESSAGE,
    priority = Priority.CRITICAL,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
public class ResourceResolverShouldBeClosed extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-6";

    public static final String RULE_MESSAGE = "ResourceResolver should be closed in finally block.";

    private static final String ACTIVATE = "Activate";

    private static final String DEACTIVATE = "Deactivate";

    protected JavaFileScannerContext context;

    private Collection<VariableTree> longResourceResolvers;

    @Override
    public void scanFile(JavaFileScannerContext javaFileScannerContext) {
        context = javaFileScannerContext;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree method) {
        if (!checkIfLongResourceResolver(method)) {
            Collection<VariableTree> resolvers = findResolversInMethod(method);
            for (VariableTree injector : resolvers) {
                if (!checkIfResourceResolverIsClosed(method, injector)) {
                    context.reportIssue(this, injector, RULE_MESSAGE);
                }
            }
        }
        super.visitMethod(method);
    }

    protected boolean checkIfLongResourceResolver(MethodTree method) {
        List<AnnotationTree> annotations = method.modifiers().annotations();
        for (AnnotationTree annotationTree : annotations) {
            if (annotationTree.annotationType().is(Tree.Kind.IDENTIFIER)) {
                IdentifierTree idf = (IdentifierTree) annotationTree.annotationType();
                if (idf.name().equals(ACTIVATE)) {
                    collectLongResourceResolverOpened(method);
                    return true;
                } else if (idf.name().equals(DEACTIVATE)) {
                    collectLongResourceResolverClosed(method);
                    return true;
                }
            }
        }
        return false;
    }

    private void collectLongResourceResolverOpened(MethodTree method) {
        longResourceResolvers = findResolversInMethod(method);
    }

    private void collectLongResourceResolverClosed(MethodTree method) {
        if (longResourceResolvers != null) {
            for (VariableTree longResourceResolver : longResourceResolvers) {
                if (!checkIfResourceResolverIsClosed(method, longResourceResolver)) {
                    context.reportIssue(this, longResourceResolver, RULE_MESSAGE);
                }
            }
        }
    }

    protected boolean checkIfResourceResolverIsClosed(MethodTree method, VariableTree injector) {
        Set<IdentifierTree> usagesOfRR = Sets.newHashSet(injector.symbol().usages());
        CheckClosedVisitor checkClosedVisitor = new CheckClosedVisitor(usagesOfRR);
        FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkClosedVisitor);
        method.accept(finallyBlockVisitor);
        return checkClosedVisitor.isClosed();
    }

    protected Collection<VariableTree> findResolversInMethod(MethodTree methodTree) {
        FindRRDeclarationVisitor findVariableDeclarationVisitor = new FindRRDeclarationVisitor();
        methodTree.accept(findVariableDeclarationVisitor);
        return findVariableDeclarationVisitor.getDeclarations();
    }

}
