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
package com.cognifide.aemrules.checks.slingquery;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import java.util.HashMap;
import java.util.Map;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.LambdaExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = SlingQueryImplicitStrategyCheck.RULE_KEY,
    name = SlingQueryImplicitStrategyCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
public class SlingQueryImplicitStrategyCheck extends BaseTreeVisitor implements JavaFileScanner {

    protected static final String RULE_KEY = "AEM-19";

    protected static final String RULE_MESSAGE = "Implicit search strategy used in Sling Query";

    private static final String FIND_METHOD = "find";

    private static final String SEARCH_STRATEGY_METHOD = "searchStrategy";

    private static final String SLING_QUERY = "SlingQuery";

    private static final String DOLLAR_SIGN = "$";

    private String slingQueryName = null;

    private Map<String, SlingQueryStates> slingQueries = new HashMap<>();

    private JavaFileScannerContext context;

    private boolean insideLambdaExpression = false;

    private boolean isDollarSlingQuery = false;

    private boolean searchStrategyOccurredInLambdaExpression = false;

    private boolean findOccurredInLambdaExpression = false;

    private boolean issueReportedInThisLambdaExpression = false;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitVariable(VariableTree tree) {
        String variableName = tree.simpleName().name();
        if (isSlingQuery(tree)) {
            slingQueries.put(variableName, SlingQueryStates.NOT_USED);
            slingQueryName = variableName;
        }
        super.visitVariable(tree);
        // This part of code will be executed directly after initialization
        if (findWithoutStrategyWasUsedOnSlingQuery()) {
            context.reportIssue(this, tree, RULE_MESSAGE);
            slingQueries.put(slingQueryName, SlingQueryStates.ISSUE_RETURNED);

            if (insideLambdaExpression) {
                issueReportedInThisLambdaExpression = true;
            }
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (isFindMethod(tree) && (slingQueryWasNotUsed() || isThisANewSlingQuery())) {
            slingQueries.replace(slingQueryName, SlingQueryStates.FIND_USED_WITHOUT_STRATEGY);
        } else if (isSearchStrategyMethod(tree)) {
            slingQueries.replace(slingQueryName, SlingQueryStates.STRATEGY_USED);
        }
        super.visitMethodInvocation(tree);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatementTree tree) {
        slingQueryName = tree.firstToken().text();
        if (isThisANewSlingQuery()) {
            slingQueries.putIfAbsent(slingQueryName, SlingQueryStates.NOT_USED);
        }
        super.visitExpressionStatement(tree);
        // This part of code will be executed directly after expression
        if (findWithoutStrategyWasUsedOnSlingQuery()) {
            context.reportIssue(this, tree, RULE_MESSAGE);
            slingQueries.put(this.slingQueryName, SlingQueryStates.ISSUE_RETURNED);
        }
    }

    @Override
    public void visitMethod(MethodTree tree) {
        slingQueries.clear();
        super.visitMethod(tree);
    }

    @Override
    public void visitIdentifier(IdentifierTree tree) {
        checkFlags(tree);
        if (shouldIssueBeReported()) {
            context.reportIssue(this, tree, RULE_MESSAGE);
            issueReportedInThisLambdaExpression = true;
        }
        super.visitIdentifier(tree);
    }

    @Override
    public void visitLambdaExpression(LambdaExpressionTree lambdaExpressionTree) {
        insideLambdaExpression = true;
        super.visitLambdaExpression(lambdaExpressionTree);
        cleaningFlags();
    }

    private boolean isSlingQuery(VariableTree tree) {
        return SLING_QUERY.equals(tree.firstToken().text());
    }

    private boolean isFindMethod(MethodInvocationTree tree) {
        return FIND_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean isSearchStrategyMethod(MethodInvocationTree tree) {
        return SEARCH_STRATEGY_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean slingQueryWasNotUsed() {
        return slingQueries.getOrDefault(slingQueryName, SlingQueryStates.NOT_USED) == SlingQueryStates.NOT_USED;
    }

    private boolean findWithoutStrategyWasUsedOnSlingQuery() {
        return slingQueries.getOrDefault(slingQueryName, SlingQueryStates.NOT_USED) == SlingQueryStates.FIND_USED_WITHOUT_STRATEGY;
    }

    private boolean isThisANewSlingQuery() {
        return DOLLAR_SIGN.equals(slingQueryName) || SLING_QUERY.equals(slingQueryName);
    }

    private boolean shouldIssueBeReported() {
        return insideLambdaExpression && isDollarSlingQuery && !issueReportedInThisLambdaExpression && !searchStrategyOccurredInLambdaExpression &&
            findOccurredInLambdaExpression;
    }

    private void cleaningFlags() {
        isDollarSlingQuery = false;
        searchStrategyOccurredInLambdaExpression = false;
        findOccurredInLambdaExpression = false;
        issueReportedInThisLambdaExpression = false;
        insideLambdaExpression = false;
    }

    private void checkFlags(IdentifierTree tree) {
        String identifier = tree.name();
        if (DOLLAR_SIGN.equals(identifier)) {
            isDollarSlingQuery = true;
        } else if (SEARCH_STRATEGY_METHOD.equals(identifier)) {
            searchStrategyOccurredInLambdaExpression = true;
        } else if (FIND_METHOD.equals(identifier)) {
            findOccurredInLambdaExpression = true;
        }
    }

    private enum SlingQueryStates {
        NOT_USED,
        FIND_USED_WITHOUT_STRATEGY,
        STRATEGY_USED,
        ISSUE_RETURNED
    }
}