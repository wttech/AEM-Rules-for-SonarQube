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
package com.cognifide.aemrules.checks;

import static org.sonar.plugins.java.api.tree.Tree.Kind.IDENTIFIER;
import static org.sonar.plugins.java.api.tree.Tree.Kind.MEMBER_SELECT;
import static org.sonar.plugins.java.api.tree.Tree.Kind.STRING_LITERAL;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.Sets;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(
    key = PreferSlingServletAnnotation.RULE_KEY,
    name = PreferSlingServletAnnotation.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    from = "6.0",
    to = "6.2"
)
public class PreferSlingServletAnnotation extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-8";

    public static final String RULE_MESSAGE = "Prefer cleaner @SlingServlet annotation.";

    public static final String PROPERTY_MESSAGE = "Property %s can be handled by @SlingServlet annotation.";

    public static final String SERVLET_RESOLVER_CONSTANTS_CLASS = "org.apache.sling.servlets.resolver.internal.ServletResolverConstants";

    public static final String NAME = "name";

    private static final Set<String> SERVLET_CONSTANTS = Sets.newHashSet(
        "SLING_SERVLET_METHODS",
        "SLING_SERVLET_EXTENSIONS",
        "SLING_SERVLET_SELECTORS",
        "SLING_SERVLET_RESOURCE_TYPES");

    private static final Set<String> SERVLET_CONSTANTS_VALUES = Sets.newHashSet(
        "sling.servlet.paths",
        "sling.servlet.resourceTypes",
        "sling.servlet.selectors",
        "sling.servlet.extensions");

    private JavaFileScannerContext context;

    private CheckAppliedAnnotationsVisitor annotations;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        gatherAppliedAnnotations(context);
        scan(context.getTree());
    }

    private void gatherAppliedAnnotations(JavaFileScannerContext context) {
        annotations = new CheckAppliedAnnotationsVisitor();
        context.getTree().accept(annotations);
    }

    @Override
    public void visitClass(ClassTree tree) {
        if (isSlingServlet(tree)) {
            scan(tree.modifiers());
            if (!annotations.hasStandardComponentAnnotation()) {
                if (!annotations.hasSlingServletAnnotation()) {
                    context.reportIssue(this, tree, RULE_MESSAGE);
                } else if (annotations.hasMixedUpAnnotations()) {
                    context.reportIssue(this, tree, "@Component nor @Service annotation is not needed when @SlingServlet is used.");
                }
            }
        }
    }

    @Override
    public void visitAnnotation(AnnotationTree annotationTree) {
        if (annotations.hasSlingServletAnnotation() && isPropertyAnnotation(annotationTree)) {
            for (ExpressionTree expression : annotationTree.arguments()) {
                if (isAssignmentToName(expression)) {
                    checkIfPropertyInsteadSlingServletIsUsed(annotationTree, (AssignmentExpressionTree) expression);
                }
            }
        }
        super.visitAnnotation(annotationTree);
    }

    private void checkIfPropertyInsteadSlingServletIsUsed(AnnotationTree annotationTree, AssignmentExpressionTree assignmentExpression) {
        ExpressionTree expression = assignmentExpression.expression();
        if (expression.is(STRING_LITERAL)) {
            LiteralTree literal = (LiteralTree) expression;
            if (SERVLET_CONSTANTS_VALUES.contains(removeQuotes(literal.value()))) {
                String message = String.format(PROPERTY_MESSAGE, literal.value());
                context.reportIssue(this, annotationTree, message);
            }
        } else if (expression.is(IDENTIFIER, MEMBER_SELECT)) {
            IdentifierTree identifier = expression.is(IDENTIFIER) ? (IdentifierTree) expression : ((MemberSelectExpressionTree) expression).identifier();
            String name = identifier.symbol().name();
            Type type = identifier.symbol().owner().type();
            if (type != null && type.fullyQualifiedName().equals(SERVLET_RESOLVER_CONSTANTS_CLASS) && SERVLET_CONSTANTS.contains(name)) {
                String message = String.format(PROPERTY_MESSAGE, name);
                context.reportIssue(this, annotationTree, message);
            }
        }
    }

    private boolean isAssignmentToName(ExpressionTree expression) {
        boolean result = false;
        if (expression.is(Tree.Kind.ASSIGNMENT)) {
            AssignmentExpressionTree assignment = (AssignmentExpressionTree) expression;
            result = NAME.equals(((IdentifierTree) assignment.variable()).name());
        }
        return result;
    }

    private boolean isPropertyAnnotation(AnnotationTree annotationTree) {
        return isOfType(annotationTree, "org.apache.felix.scr.annotations.Property");
    }

    private boolean isOfType(AnnotationTree annotationTree, String fullyQualifiedName) {
        return annotationTree.annotationType().symbolType().fullyQualifiedName().equals(fullyQualifiedName);
    }

    private boolean isSlingServlet(ClassTree tree) {
        Type type = tree.symbol().type();
        boolean allMethodsServlet = type.isSubtypeOf("org.apache.sling.api.servlets.SlingAllMethodsServlet");
        boolean safeMethodsServlet = type.isSubtypeOf("org.apache.sling.api.servlets.SlingSafeMethodsServlet");
        return allMethodsServlet || safeMethodsServlet;
    }

    private String removeQuotes(String value) {
        return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
    }

    private class CheckAppliedAnnotationsVisitor extends BaseTreeVisitor {

        private boolean legacyServiceAnnotation;

        private boolean legacyComponentAnnotation;

        private boolean standardComponentAnnotation;

        private boolean slingServletAnnotation;

        @Override
        public void visitAnnotation(AnnotationTree annotationTree) {
            standardComponentAnnotation |= isOfType(annotationTree, "org.osgi.service.component.annotations.Component");
            legacyServiceAnnotation |= isOfType(annotationTree, "org.apache.felix.scr.annotations.Service");
            legacyComponentAnnotation |= isOfType(annotationTree, "org.apache.felix.scr.annotations.Component");
            slingServletAnnotation |= isOfType(annotationTree, "org.apache.felix.scr.annotations.sling.SlingServlet");
            super.visitAnnotation(annotationTree);
        }

        public boolean hasMixedUpAnnotations() {
            return slingServletAnnotation && (legacyServiceAnnotation || legacyComponentAnnotation);
        }

        public boolean hasSlingServletAnnotation() {
            return slingServletAnnotation;
        }

        public boolean hasStandardComponentAnnotation() {
            return standardComponentAnnotation;
        }
    }
}
