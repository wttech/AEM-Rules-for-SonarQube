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
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.TypeTree;

class TypeTreeVisitor extends BaseTreeVisitor {

    private boolean resourceTypeInstance;

    public boolean isResourceTypeInstance() {
        return resourceTypeInstance;
    }

    @Override
    public void visitIdentifier(IdentifierTree tree) {
        isResourceType(tree);
        super.visitIdentifier(tree);
    }

    @Override
    public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
        isResourceType(tree);
        super.visitMemberSelectExpression(tree);
    }

    private void isResourceType(TypeTree typeTree) {
        if (typeTree.symbolType().isSubtypeOf(Constants.RESOURCE_TYPE)) {
            resourceTypeInstance = true;
        }
    }

}
