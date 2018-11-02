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
package com.cognifide.aemrules.java.checks.slingquery;

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
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
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

    private static final String APACHE_SLING_QUERY = "org.apache.sling.query.SlingQuery";

    private static final String SLING_QUERY = "SlingQuery";

    private static final String DOLLAR_SIGN = "$";

    private String currentSlingQueryVariableName = null;

    private Map<String, SlingQueryStates> slingQueries = new HashMap<>();

    private boolean findMethodUsed = false;

    private boolean searchStrategyMethodUsed = false;

    private boolean ignoreIssues = false;

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitVariable(VariableTree tree) {
        if (isSlingQuery(tree)) {
            slingQueries.put(tree.simpleName().name(), SlingQueryStates.NOT_USED);
            currentSlingQueryVariableName = tree.simpleName().name();
        }
        super.visitVariable(tree);
        // This part of code will be executed directly after initialization
        reportIssueIfStrategyNotUsed(tree, currentSlingQueryVariableName);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatementTree tree) {
        currentSlingQueryVariableName = tree.firstToken().text();
        if (isThisANewSlingQuery()) {
            slingQueries.putIfAbsent(currentSlingQueryVariableName, SlingQueryStates.NOT_USED);
        }
        super.visitExpressionStatement(tree);
        // This part of code will be executed directly after expression
        reportIssueIfStrategyNotUsed(tree, currentSlingQueryVariableName);
    }

    private void reportIssueIfStrategyNotUsed(Tree tree, String variableName) {
        if (findWithoutStrategyWasUsedOnSlingQuery()) {
            context.reportIssue(this, tree, RULE_MESSAGE);
            slingQueries.put(variableName, SlingQueryStates.ISSUE_RETURNED);
            ignoreIssues = true;
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        // Method invocation from right to left
        if (isFindMethod(tree) && (slingQueryWasNotUsed() || isThisANewSlingQuery())) {
            slingQueries.replace(currentSlingQueryVariableName, SlingQueryStates.FIND_USED_WITHOUT_STRATEGY);
        } else if (isSearchStrategyMethod(tree)) {
            slingQueries.replace(currentSlingQueryVariableName, SlingQueryStates.STRATEGY_USED);
            ignoreIssues = true;
        }
        if (DOLLAR_SIGN.equals(tree.methodSelect().firstToken().text()) && isDollarCase()) {
            context.reportIssue(this, tree, RULE_MESSAGE);
        } else if (isFindMethod(tree)) {
            findMethodUsed = true;
        } else if (isSearchStrategyMethod(tree)) {
            searchStrategyMethodUsed = true;
        }
        super.visitMethodInvocation(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        clean();
        super.visitMethod(tree);
    }

    private boolean isSlingQuery(VariableTree tree) {
        return APACHE_SLING_QUERY.equals(tree.type().symbolType().fullyQualifiedName());
    }

    private boolean isFindMethod(MethodInvocationTree tree) {
        return FIND_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean isSearchStrategyMethod(MethodInvocationTree tree) {
        return SEARCH_STRATEGY_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean slingQueryWasNotUsed() {
        return slingQueries.getOrDefault(currentSlingQueryVariableName, SlingQueryStates.NOT_USED) == SlingQueryStates.NOT_USED;
    }

    private boolean findWithoutStrategyWasUsedOnSlingQuery() {
        return slingQueries.getOrDefault(currentSlingQueryVariableName, SlingQueryStates.NOT_USED) == SlingQueryStates.FIND_USED_WITHOUT_STRATEGY;
    }

    private boolean isThisANewSlingQuery() {
        return DOLLAR_SIGN.equals(currentSlingQueryVariableName) || SLING_QUERY.equals(currentSlingQueryVariableName);
    }

    private boolean isDollarCase() {
        return findMethodUsed && !searchStrategyMethodUsed && !ignoreIssues;
    }

    private void clean() {
        slingQueries.clear();
        findMethodUsed = false;
        searchStrategyMethodUsed = false;
        ignoreIssues = false;
    }

    private enum SlingQueryStates {
        NOT_USED,
        FIND_USED_WITHOUT_STRATEGY,
        STRATEGY_USED,
        ISSUE_RETURNED
    }
}