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

import java.util.List;
import java.util.function.Predicate;
import org.sonar.plugins.java.api.semantic.Type;

@FunctionalInterface
public interface MethodParametersPredicate extends Predicate<List<Type>> {

    static MethodParametersPredicate of(List<ParameterTypePredicate> expectedParameterTypes) {
        return actualParameterTypes -> allParameterTypesMatch(actualParameterTypes, expectedParameterTypes);
    }

    static boolean allParameterTypesMatch(List<Type> actualParameterTypes, List<ParameterTypePredicate> expectedParameterTypes) {
        if (actualParameterTypes.size() != expectedParameterTypes.size()) {
            return false;
        }

        boolean result = true;
        for (int parameterTypeIndex = 0; parameterTypeIndex < actualParameterTypes.size(); parameterTypeIndex++) {
            if (!expectedParameterTypes.get(parameterTypeIndex).test(actualParameterTypes.get(parameterTypeIndex))) {
                result = false;
            }
        }
        return result;
    }

}
