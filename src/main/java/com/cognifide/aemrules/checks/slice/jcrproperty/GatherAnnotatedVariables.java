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
package com.cognifide.aemrules.checks.slice.jcrproperty;

import com.cognifide.aemrules.util.TypeUtils;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.VariableTree;

class GatherAnnotatedVariables extends BaseTreeVisitor {

    private static final String JCR_PROPERTY_ANNOTATION = "com.cognifide.slice.mapper.annotation.JcrProperty";

    private final Set<String> annotatedVariables = new HashSet<>();

    public Set<String> getAnnotatedVariables() {
        return Collections.unmodifiableSet(annotatedVariables);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        if (hasJcrPropertyAnnotation(tree)) {
            annotatedVariables.add(tree.symbol().name());
        }
        super.visitVariable(tree);
    }

    private boolean hasJcrPropertyAnnotation(VariableTree tree) {
        boolean jcrPropertyAnnotationPresent = false;
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            jcrPropertyAnnotationPresent |= TypeUtils.isOfType(annotation.annotationType(), JCR_PROPERTY_ANNOTATION);
        }
        return jcrPropertyAnnotationPresent;
    }

}
