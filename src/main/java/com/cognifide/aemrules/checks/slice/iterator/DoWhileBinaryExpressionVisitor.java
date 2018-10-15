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

import com.cognifide.aemrules.Constants;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;

class DoWhileBinaryExpressionVisitor extends BaseTreeVisitor {

    private boolean containsResourceComparison;

    public boolean containsResourceComparison() {
        return containsResourceComparison;
    }

    @Override
    public void visitBinaryExpression(BinaryExpressionTree tree) {
        containsResourceComparison = hasResourceComparison(tree);
        super.visitBinaryExpression(tree);
    }

    private boolean hasResourceComparison(BinaryExpressionTree tree) {
        return isResource(tree.leftOperand()) || isResource(tree.rightOperand());
    }

    private boolean isResource(ExpressionTree operand) {
        return !operand.is(Tree.Kind.NULL_LITERAL) && operand.symbolType().isSubtypeOf(Constants.RESOURCE_TYPE);
    }

}
