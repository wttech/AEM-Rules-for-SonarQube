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

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.VariableTree;

class VariableTreeVisitor extends BaseTreeVisitor {

    private boolean withResourceTypeVariable;

    public boolean hasResourceTypeVariable() {
        return withResourceTypeVariable;
    }

    @Override
    public void visitVariable(VariableTree variableTree) {
        ParameterizedTypeTreeVisitor treeVisitor = new ParameterizedTypeTreeVisitor();
        variableTree.type().accept(treeVisitor);
        withResourceTypeVariable = treeVisitor.hasResourceTypeVariable();
        super.visitVariable(variableTree);
    }

}
