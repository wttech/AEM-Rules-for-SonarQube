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

import com.cognifide.aemrules.checks.visitors.CheckClosedVisitor;
import com.cognifide.aemrules.checks.visitors.FinallyBlockVisitor;
import com.cognifide.aemrules.checks.visitors.FindVariableDeclarationVisitor;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = InjectorShouldBeClosedCheck.RULE_KEY,
    name = InjectorShouldBeClosedCheck.RULE_MESSAGE,
    priority = Priority.CRITICAL,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
public class InjectorShouldBeClosedCheck extends BaseTreeVisitor implements JavaFileScanner {

    protected static final String RULE_KEY = "AEM-4";

    protected static final String RULE_MESSAGE = "Injector should be closed in finally block or created as a resource within try block.";

    private static final String CLASS_INJECTOR_NAME = "com.cognifide.slice.api.injector.InjectorWithContext";

    protected JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext javaFileScannerContext) {
        context = javaFileScannerContext;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree method) {
        List<VariableTree> injectors = findInjectorsInMethod(method);
        for (VariableTree injector : injectors) {
            boolean closed = checkIfInjectorIsClosedInMethod(method, injector);
            if (!closed) {
                context.reportIssue(this, injector, RULE_MESSAGE);
            }
        }
        super.visitMethod(method);
    }

    protected boolean checkIfInjectorIsClosedInMethod(MethodTree method, VariableTree injector) {
        Set<IdentifierTree> usagesOfInjector = Sets.newHashSet(injector.symbol().usages());
        CheckClosedVisitor checkClosedVisitor = new CheckClosedVisitor(usagesOfInjector);
        FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkClosedVisitor);
        method.accept(finallyBlockVisitor);
        return checkClosedVisitor.isClosed();
    }

    protected List<VariableTree> findInjectorsInMethod(MethodTree methodTree) {
        FindVariableDeclarationVisitor varVisitor = new FindVariableDeclarationVisitor(CLASS_INJECTOR_NAME);
        methodTree.accept(varVisitor);
        return varVisitor.getDeclarations();
    }

}
