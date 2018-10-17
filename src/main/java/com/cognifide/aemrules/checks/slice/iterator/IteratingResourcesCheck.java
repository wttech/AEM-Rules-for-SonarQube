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

import static org.sonar.plugins.java.api.tree.Tree.Kind.IDENTIFIER;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.DoWhileStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;

@Rule(
    key = IteratingResourcesCheck.RULE_KEY,
    name = IteratingResourcesCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = {Tags.AEM, Tags.SLICE}
)
@AemVersion(
    all = true
)
public class IteratingResourcesCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-10";

    public static final String RULE_MESSAGE = "Use ModelProvider#getListFromResources instead of iteration";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitWhileStatement(WhileStatementTree tree) {
        checkModelProviderInLoop(tree, tree.condition(), tree.statement());
        super.visitWhileStatement(tree);
    }

    @Override
    public void visitDoWhileStatement(DoWhileStatementTree tree) {
        if (tree.condition() instanceof BinaryExpressionTree) {
            DoWhileBinaryExpressionVisitor visitor = new DoWhileBinaryExpressionVisitor();
            tree.condition().accept(visitor);
            if (visitor.containsResourceComparison()) {
                checkModelProviderCall(tree, tree.statement());
            }
        }
        super.visitDoWhileStatement(tree);
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {
        checkModelProviderInLoop(tree, tree.condition(), tree.statement());
        super.visitForStatement(tree);
    }

    @Override
    public void visitForEachStatement(ForEachStatement tree) {
        ExpressionTree expression = tree.expression();
        if (expression instanceof IdentifierTree && isResourceIterator((IdentifierTree) expression)) {
            checkModelProviderCall(tree, tree.statement());
        }
        super.visitForEachStatement(tree);
    }

    private void checkModelProviderInLoop(Tree tree, ExpressionTree condition, StatementTree statement) {
        if (isIteratingOverResources(condition)) {
            checkModelProviderCall(tree, statement);
        }
    }

    private void checkModelProviderCall(Tree tree, StatementTree statement) {
        MethodInvocationTreeVisitor methodInvocationTreeVisitor = new MethodInvocationTreeVisitor();
        statement.accept(methodInvocationTreeVisitor);
        if (methodInvocationTreeVisitor.isModelProviderGetCalled()) {
            context.reportIssue(this, tree, RULE_MESSAGE);
        }
    }

    private boolean isIteratingOverResources(ExpressionTree condition) {
        if (!(condition instanceof MethodInvocationTree)) {
            return false;
        }
        boolean resourceIterator = false;
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) condition;
        boolean subtypeOfIterator = methodInvocationTree.symbol().owner().type().isSubtypeOf("java.util.Iterator");
        if (subtypeOfIterator) {
            ExpressionTree expression = ((MemberSelectExpressionTree) methodInvocationTree.methodSelect()).expression();
            if (expression.is(IDENTIFIER)) {
                resourceIterator = isResourceIterator((IdentifierTree) expression);
            }
        }
        return subtypeOfIterator && resourceIterator;
    }

    private boolean isResourceIterator(IdentifierTree expression) {
        boolean result = false;
        Tree declaration = expression.symbol().declaration();
        if (null != declaration) {
            VariableTreeVisitor treeVisitor = new VariableTreeVisitor();
            declaration.accept(treeVisitor);
            result = treeVisitor.hasResourceTypeVariable();
        }
        return result;
    }

}
