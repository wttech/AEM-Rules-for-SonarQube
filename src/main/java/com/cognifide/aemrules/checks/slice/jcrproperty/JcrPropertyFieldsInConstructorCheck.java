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

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.util.TypeUtils;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.Sets;
import java.util.Set;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = JcrPropertyFieldsInConstructorCheck.RULE_KEY,
    name = JcrPropertyFieldsInConstructorCheck.RULE_MESSAGE,
    priority = Priority.MAJOR,
    tags = {Tags.AEM, Tags.SLICE}
)
@AemVersion(
    all = true
)
public class JcrPropertyFieldsInConstructorCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-12";

    public static final String RULE_MESSAGE = "Fields annotated by @JcrProperty shouldn't be accessed from constructor.";

    private static final String SLICE_RESOURCE_ANNOTATION = "com.cognifide.slice.mapper.annotation.SliceResource";

    private final Set<String> annotatedVariables = Sets.newHashSet();

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree classTree) {
        if (hasSliceResourceAnnotation(classTree)) {
            GatherAnnotatedVariables gatherAnnotatedVariables = new GatherAnnotatedVariables();
            classTree.accept(gatherAnnotatedVariables);
            annotatedVariables.addAll(gatherAnnotatedVariables.getAnnotatedVariables());
            super.visitClass(classTree);
        }
    }

    private boolean hasSliceResourceAnnotation(ClassTree classTree) {
        boolean sliceResourceAnnotationPresent = false;
        for (AnnotationTree annotation : classTree.modifiers().annotations()) {
            sliceResourceAnnotationPresent |= TypeUtils.isOfType(annotation.annotationType(), SLICE_RESOURCE_ANNOTATION);
        }
        return sliceResourceAnnotationPresent;
    }

    @Override
    public void visitMethod(MethodTree tree) {
        if (tree.is(MethodTree.Kind.CONSTRUCTOR)) {
            tree.accept(new CheckUsageInConstructorVisitor(this, context, methodParams(tree)));
        }
        super.visitMethod(tree);
    }

    private Set<String> methodParams(MethodTree tree) {
        Set<String> params = Sets.newHashSet();
        for (VariableTree var : tree.parameters()) {
            params.add(var.symbol().name());
        }
        return params;
    }

    private class CheckUsageInConstructorVisitor extends BaseTreeVisitor {

        private final JavaCheck javaCheck;

        private final JavaFileScannerContext context;

        private final Set<String> methodParams;

        private CheckUsageInConstructorVisitor(JavaCheck javaCheck, JavaFileScannerContext context, final Set<String> methodParams) {
            this.javaCheck = javaCheck;
            this.context = context;
            this.methodParams = methodParams;
        }

        @Override
        public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
            if (annotatedVariables.contains(tree.identifier().symbol().name())) {
                context.reportIssue(javaCheck, tree, RULE_MESSAGE);
            }
            super.visitMemberSelectExpression(tree);
        }

        @Override
        public void visitIdentifier(IdentifierTree tree) {
            if (tree.symbol().isVariableSymbol()) {
                final String name = tree.symbol().name();
                if (!methodParams.contains(name) && annotatedVariables.contains(name)) {
                    context.reportIssue(javaCheck, tree, RULE_MESSAGE);
                }
            }
            super.visitIdentifier(tree);
        }
    }
}
