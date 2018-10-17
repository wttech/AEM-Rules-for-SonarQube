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
package com.cognifide.aemrules.checks.slice.session;

import static com.cognifide.aemrules.checks.slice.session.ModelsShouldNotUseSessionCheck.RULE_MESSAGE;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;

import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

/**
 * @author Krzysztof Watral
 */
class SessionUsageVisitor extends BaseTreeVisitor {

    private static final Set<String> SESSION_CLASSES = ImmutableSet.of(
        "javax.jcr.Session",
        "com.day.cq.wcm.api.PageManager",
        "org.apache.sling.api.SlingHttpServletRequest",
        "org.apache.sling.api.resource.ResourceResolver");

    private final JavaFileScanner javaFileScanner;

    private final JavaFileScannerContext context;

    SessionUsageVisitor(JavaFileScanner javaFileScanner, JavaFileScannerContext context) {
        this.javaFileScanner = javaFileScanner;
        this.context = context;
    }

    @Override
    public void visitNewClass(NewClassTree tree) {
        // ignore anonymous classes here
    }

    @Override
    public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
        if (isSubtypeOfSessionClass(tree.expression())) {
            context.reportIssue(javaFileScanner, tree, RULE_MESSAGE);
        }
        super.visitMemberSelectExpression(tree);
    }

    @Override
    public void visitReturnStatement(ReturnStatementTree tree) {
        ExpressionTree expression = tree.expression();
        if (isNotNullLiteral(expression) && isSubtypeOfSessionClass(expression)) {
            context.reportIssue(javaFileScanner, tree, RULE_MESSAGE);
        }
        super.visitReturnStatement(tree);
    }

    private boolean isNotNullLiteral(ExpressionTree expression) {
        return null != expression && !expression.is(Kind.NULL_LITERAL);
    }

    private boolean isSubtypeOfSessionClass(ExpressionTree expression) {
        boolean isSubtype = false;
        if (null != expression) {
            final Type type = expression.symbolType();
            for (String sessionClass : SESSION_CLASSES) {
                if (type.isSubtypeOf(sessionClass)) {
                    isSubtype = true;
                    break;
                }
            }
        }
        return isSubtype;
    }
}
