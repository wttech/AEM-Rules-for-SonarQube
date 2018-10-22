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
package com.cognifide.aemrules.checks.visitors;

import com.google.common.collect.Sets;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Collection;
import java.util.Set;

/**
 * Finds all injector variable declarations. Used in method's bodies only.
 */
public class FindSessionDeclarationVisitor extends BaseTreeVisitor {

    private static final String JCR_SESSION = "javax.jcr.Session";

    private static final String SLING_REPOSITORY = "org.apache.sling.jcr.api.SlingRepository";

    private final Set<VariableTree> sessions;

    public FindSessionDeclarationVisitor() {
        sessions = Sets.newHashSet();
    }

    public Collection<VariableTree> getDeclarations() {
        return sessions;
    }

    @Override
    public void visitAssignmentExpression(AssignmentExpressionTree tree) {
        if (isMethodInvocation(tree)) {
            MethodInvocationTree methodInvocation = (MethodInvocationTree) tree.expression();
            if (isManuallyCreatedSession(methodInvocation)) {
                IdentifierTree variable = (IdentifierTree) tree.variable();
                sessions.add((VariableTree) getDeclaration(variable));
            } else if (isSession(methodInvocation) && methodInvocation.methodSelect().is(Kind.IDENTIFIER)) {
                findSessionsCreatedInMethods(tree, methodInvocation);
            }
        }
        super.visitAssignmentExpression(tree);
    }

    private void findSessionsCreatedInMethods(AssignmentExpressionTree tree, MethodInvocationTree methodInvocation) {
        MethodTree methodDeclaration = getMethodTree(methodInvocation);
        if (isManuallyCreatedSession(methodDeclaration)) {
            IdentifierTree identifierTree = null;
            if (tree.variable().is(Kind.IDENTIFIER)) {
                identifierTree = (IdentifierTree) tree.variable();
            } else if (tree.variable().is(Kind.MEMBER_SELECT)) {
                identifierTree = ((MemberSelectExpressionTree) tree.variable()).identifier();
            }
            if (identifierTree != null) {
                sessions.add((VariableTree) getDeclaration(identifierTree));
            }
        }
    }

    private boolean isManuallyCreatedSession(MethodTree methodDeclaration) {
        CheckIfSessionCreatedManually sessionCreatedManually = new CheckIfSessionCreatedManually();
        methodDeclaration.accept(sessionCreatedManually);
        return sessionCreatedManually.isCreatedManually();
    }

    private MethodTree getMethodTree(MethodInvocationTree methodInvocation) {
        IdentifierTree method = (IdentifierTree) methodInvocation.methodSelect();
        return (MethodTree) getDeclaration(method);
    }

    private Tree getDeclaration(IdentifierTree variable) {
        return variable.symbol().declaration();
    }

    private boolean isManuallyCreatedSession(MethodInvocationTree methodInvocation) {
        return isSession(methodInvocation) && isSlingRepository(methodInvocation);
    }

    private boolean isMethodInvocation(AssignmentExpressionTree tree) {
        return tree.expression().is(Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitReturnStatement(ReturnStatementTree tree) {
        if (tree.expression() != null && tree.expression().is(Kind.IDENTIFIER)) {
            IdentifierTree identifier = (IdentifierTree) tree.expression();
            Tree declaration = getDeclaration(identifier);
            if (sessions.contains(declaration)) {
                sessions.remove(declaration);
            }
        }
        super.visitReturnStatement(tree);
    }

    private boolean isSlingRepository(MethodInvocationTree methodInvocation) {
        return methodInvocation.symbol().owner().type().fullyQualifiedName().equals(SLING_REPOSITORY);
    }

    private boolean isSession(MethodInvocationTree methodInvocation) {
        return methodInvocation.symbolType().fullyQualifiedName().equals(JCR_SESSION);
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {
        // omit resources
        scan(tree.block());
        scan(tree.catches());
        scan(tree.finallyBlock());
    }

    private class CheckIfSessionCreatedManually extends BaseTreeVisitor {

        private Tree declarationOfReturnedVariable;

        private boolean createdManually;

        @Override
        public void visitMethod(MethodTree tree) {
            FindDeclarationOfReturnedVariable visitor = new FindDeclarationOfReturnedVariable();
            tree.accept(visitor);
            declarationOfReturnedVariable = visitor.getDeclarationOfReturnedVariable();
            super.visitMethod(tree);
        }

        @Override
        public void visitAssignmentExpression(AssignmentExpressionTree tree) {
            if (isMethodInvocation(tree) && getDeclaration((IdentifierTree) tree.variable()).equals(declarationOfReturnedVariable)) {
                MethodInvocationTree methodInvocation = (MethodInvocationTree) tree.expression();
                if (isManuallyCreatedSession(methodInvocation)) {
                    this.createdManually = true;
                } else {
                    CheckIfSessionCreatedManually sessionCreatedManually = new CheckIfSessionCreatedManually();
                    getMethodTree(methodInvocation).accept(sessionCreatedManually);
                    this.createdManually = sessionCreatedManually.isCreatedManually();
                }
            }
            super.visitAssignmentExpression(tree);
        }

        public boolean isCreatedManually() {
            return createdManually;
        }

    }

    private class FindDeclarationOfReturnedVariable extends BaseTreeVisitor {

        private Tree declarationOfReturnedVariable;

        @Override
        public void visitReturnStatement(ReturnStatementTree tree) {
            if (tree.expression() != null && tree.expression().is(Kind.IDENTIFIER)) {
                IdentifierTree identifier = (IdentifierTree) tree.expression();
                declarationOfReturnedVariable = getDeclaration(identifier);
            }
            super.visitReturnStatement(tree);
        }

        public Tree getDeclarationOfReturnedVariable() {
            return declarationOfReturnedVariable;
        }

    }

}