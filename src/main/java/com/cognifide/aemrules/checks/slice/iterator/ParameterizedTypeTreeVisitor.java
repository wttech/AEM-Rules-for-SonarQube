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

import java.util.Iterator;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ParameterizedTypeTree;
import org.sonar.plugins.java.api.tree.Tree;

class ParameterizedTypeTreeVisitor extends BaseTreeVisitor {

    private boolean withResourceTypeVariable;

    public boolean hasResourceTypeVariable() {
        return withResourceTypeVariable;
    }

    @Override
    public void visitParameterizedType(ParameterizedTypeTree parameterizedTypeTree) {
        for (Iterator<Tree> iterator = parameterizedTypeTree.typeArguments().iterator(); iterator.hasNext() && !withResourceTypeVariable; ) {
            TypeTreeVisitor treeVisitor = new TypeTreeVisitor();
            iterator.next().accept(treeVisitor);
            withResourceTypeVariable = treeVisitor.isResourceTypeInstance();
        }
        super.visitParameterizedType(parameterizedTypeTree);
    }

}
