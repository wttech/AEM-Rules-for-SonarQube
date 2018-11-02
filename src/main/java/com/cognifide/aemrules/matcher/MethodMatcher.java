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
package com.cognifide.aemrules.matcher;

import java.util.Arrays;
import java.util.List;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

public final class MethodMatcher {

    private OwnerTypePredicate methodOwnerTypePredicate;

    private MethodNamePredicate methodNamePredicate;

    private MethodParametersPredicate methodParametersPredicate;

    private MethodMatcher(MethodNamePredicate methodNamePredicate, OwnerTypePredicate methodOwnerTypePredicate,
        List<ParameterTypePredicate> methodParameterTypePredicates) {
        this.methodNamePredicate = methodNamePredicate;
        this.methodOwnerTypePredicate = methodOwnerTypePredicate;
        this.methodParametersPredicate = MethodParametersPredicate.of(methodParameterTypePredicates);
    }

    public static MethodMatcher create(MethodNamePredicate methodNamePredicate, OwnerTypePredicate methodOwnerTypePredicate,
        ParameterTypePredicate... methodParameterTypePredicates) {
        return new MethodMatcher(methodNamePredicate, methodOwnerTypePredicate, Arrays.asList(methodParameterTypePredicates));
    }

    private static IdentifierTree getIdentifier(MethodInvocationTree methodInvocationTree) {
        // methodSelect can only be the Tree.Kind.IDENTIFIER or the Tree.Kind.MEMBER_SELECT
        if (methodInvocationTree.methodSelect().is(Tree.Kind.IDENTIFIER)) {
            return (IdentifierTree) methodInvocationTree.methodSelect();
        }
        return ((MemberSelectExpressionTree) methodInvocationTree.methodSelect()).identifier();
    }

    public boolean matches(MethodInvocationTree methodInvocationTree) {
        IdentifierTree identifier = getIdentifier(methodInvocationTree);
        return matches(identifier.symbol());
    }

    private boolean matches(Symbol symbol) {
        return symbol.isMethodSymbol() && isSearchedMethod((MethodSymbol) symbol);
    }

    private boolean isSearchedMethod(MethodSymbol symbol) {
        return isMethodNameAcceptable(symbol) && allMethodParametersAcceptable(symbol) && methodOwnerTypePredicate.test(symbol.owner().type());
    }

    private boolean isMethodNameAcceptable(MethodSymbol symbol) {
        return methodNamePredicate.test(symbol.name());
    }

    private boolean allMethodParametersAcceptable(MethodSymbol methodSymbol) {
        return methodParametersPredicate.test(methodSymbol.parameterTypes());
    }

}
