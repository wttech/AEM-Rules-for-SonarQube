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

import com.cognifide.aemrules.checks.visitors.CheckLoggedOutVisitor;
import com.cognifide.aemrules.checks.visitors.FinallyBlockVisitor;
import com.cognifide.aemrules.checks.visitors.FindSessionDeclarationVisitor;
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
    key = SessionShouldBeLoggedOut.RULE_KEY,
    name = SessionShouldBeLoggedOut.RULE_MESSAGE,
    priority = Priority.CRITICAL,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
public class SessionShouldBeLoggedOut extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-7";

    public static final String RULE_MESSAGE = "Session should be logged out in finally block.";

    private static final String ACTIVATE = "Activate";

    private static final String DEACTIVATE = "Deactivate";

    protected JavaFileScannerContext context;

    private Collection<VariableTree> longSessions;

    @Override
    public void scanFile(JavaFileScannerContext javaFileScannerContext) {
        context = javaFileScannerContext;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree method) {
        if (!checkIfLongSession(method)) {
            Collection<VariableTree> sessions = findSessionsInMethod(method);
            for (VariableTree session : sessions) {
                if (!checkIfLoggedOut(method, session)) {
                    context.reportIssue(this, session, RULE_MESSAGE);
                }
            }
        }
        super.visitMethod(method);
    }

    protected boolean checkIfLongSession(MethodTree method) {
        List<AnnotationTree> annotations = method.modifiers().annotations();
        for (AnnotationTree annotationTree : annotations) {
            if (annotationTree.annotationType().is(Tree.Kind.IDENTIFIER)) {
                IdentifierTree idf = (IdentifierTree) annotationTree.annotationType();
                if (idf.name().equals(ACTIVATE)) {
                    collectLongSessionOpened(method);
                    return true;
                } else if (idf.name().equals(DEACTIVATE)) {
                    collectLongSessionClosed(method);
                    return true;
                }
            }
        }
        return false;
    }

    private void collectLongSessionOpened(MethodTree method) {
        longSessions = findSessionsInMethod(method);
    }

    private void collectLongSessionClosed(MethodTree method) {
        if (longSessions != null) {
            for (VariableTree longSession : longSessions) {
                if (!checkIfLoggedOut(method, longSession)) {
                    context.reportIssue(this, longSession, RULE_MESSAGE);
                }
            }
        }
    }

    protected boolean checkIfLoggedOut(MethodTree method, VariableTree injector) {
        Set<IdentifierTree> usagesOfSession = Sets.newHashSet(injector.symbol().usages());
        CheckLoggedOutVisitor checkLoggedOutVisitor = new CheckLoggedOutVisitor(usagesOfSession);
        FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkLoggedOutVisitor);
        method.accept(finallyBlockVisitor);
        return checkLoggedOutVisitor.isLoggedOut();
    }

    protected Collection<VariableTree> findSessionsInMethod(MethodTree methodTree) {
        FindSessionDeclarationVisitor findSessionDeclarationVisitor = new FindSessionDeclarationVisitor();
        methodTree.accept(findSessionDeclarationVisitor);
        return findSessionDeclarationVisitor.getDeclarations();
    }

}
