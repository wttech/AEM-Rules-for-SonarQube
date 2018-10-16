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

import com.cognifide.aemrules.Constants;
import com.cognifide.aemrules.tag.Tags;
import java.util.HashMap;
import java.util.Map;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = ContentResourceShouldBeNullCheckedCheck.RULE_KEY,
    name = ContentResourceShouldBeNullCheckedCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
public class ContentResourceShouldBeNullCheckedCheck extends BaseTreeVisitor implements
    JavaFileScanner {

    public static final String RULE_KEY = "AEM-18";

    public static final String RULE_MESSAGE = "Always null check the returned value of Page.getContentResource() method";

    public static final String GET_CONTENT_RESOURCE_METHOD = "getContentResource";

    public static final String NOT_EQUAL = "NOT_EQUAL_TO";

    public static final String EQUAL = "EQUAL_TO";

    public static final String NON_NULL_METHOD = "nonNull";

    public static final String ALL_NON_NULL_METHOD = "allNotNull";

    public static final String IS_NULL_METHOD = "isNull";

    public static final int FIRST_ARGUMENT = 0;

    private boolean returnOccurredInsideEqualNullCheck = false;

    private boolean insideEqualNullCheck = false;

    private boolean insideIfStatement = false;

    private JavaFileScannerContext context;

    private Map<String, Boolean> contentResources = new HashMap();

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitIfStatement(IfStatementTree tree) {
        insideIfStatement = true;
        if (isThisAResourceNullCheck(tree, NOT_EQUAL)) {
            updateNullCheckedResources(tree, true);
        } else if (isThisAResourceNullCheck(tree, EQUAL)) {
            insideEqualNullCheck = true;
        }
        super.visitIfStatement(tree);
        if (returnOccurredInsideEqualNullCheck) {
            updateNullCheckedResources(tree, true);
        }
        cleanFlagsAfterIfStatement(tree);
    }

    @Override
    public void visitAssignmentExpression(AssignmentExpressionTree tree) {
        if (isGetContentResourceUsedOnResource(tree)) {
            contentResources.put(tree.variable().firstToken().text(), false);
        }
        super.visitAssignmentExpression(tree);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        externalLibraryNullChecks(tree);
        equalsNullCheck(tree);
        chainedMethodsNullCheck(tree);
        super.visitMethodInvocation(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        if (isResourceInitialized(tree)) {
            if (isGetContentResourceUsedOnPage(tree)) {
                contentResources.put(tree.simpleName().name(), false);
            }
        }
        super.visitVariable(tree);
    }

    @Override
    public void visitClass(ClassTree tree) {
        contentResources.clear();
        super.visitClass(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        super.visitMethod(tree);
        returnOccurredInsideEqualNullCheck = false;
    }

    @Override
    public void visitReturnStatement(ReturnStatementTree tree) {
        if (insideEqualNullCheck) {
            returnOccurredInsideEqualNullCheck = true;
        }
        super.visitReturnStatement(tree);
    }

    private void cleanFlagsAfterIfStatement(IfStatementTree tree) {
        insideIfStatement = false;
        updateNullCheckedResources(tree, false);
        insideEqualNullCheck = false;
    }

    private void equalsNullCheck(MethodInvocationTree tree) {
        if (!contentResources.getOrDefault(tree.firstToken().text(), true) &&
            !returnOccurredInsideEqualNullCheck) {
            context.reportIssue(this, tree, RULE_MESSAGE);
        }
    }

    private void updateNullCheckedResources(IfStatementTree tree, boolean value) {
        if (isRightSideNullCheck(tree)) {
            contentResources.replace(tree.condition().firstToken().text(), value);
        } else {
            contentResources.replace(tree.condition().lastToken().text(), value);
        }
    }

    private void externalLibraryNullChecks(MethodInvocationTree tree) {
        if (isNonNullUsed(tree)) {
            contentResources.replace(tree.arguments().get(FIRST_ARGUMENT).toString(), true);
        } else if (isAllNonNullUsed(tree)) {
            tree.arguments().forEach(contentResource -> contentResources.replace(contentResource.toString(), true));
        } else if (insideIfStatement && isThisIsNullMethod(tree)) {
            insideEqualNullCheck = true;
        }
    }

    private void chainedMethodsNullCheck(MethodInvocationTree tree) {
        if (tree.methodSelect() instanceof MemberSelectExpressionTree) {
            MemberSelectExpressionTree method = (MemberSelectExpressionTree) tree.methodSelect();
            if (method.expression() instanceof MethodInvocationTree) {
                MethodInvocationTree invocation = (MethodInvocationTree) method.expression();
                if (!invocation.symbol().isUnknown() &&
                    isPage(invocation.symbol().owner().type().fullyQualifiedName()) &&
                    isGetContentResourceUsedOnPage(invocation) &&
                    !returnOccurredInsideEqualNullCheck) {
                    context.reportIssue(this, tree, RULE_MESSAGE);
                }
            }
        }
    }

    private boolean isResource(AssignmentExpressionTree tree) {
        return Constants.RESOURCE_TYPE.equals(tree.variable().symbolType().fullyQualifiedName());
    }

    private boolean isResourceInitialized(VariableTree tree) {
        return tree.initializer() != null && tree.initializer().kind() != Kind.NULL_LITERAL;
    }

    private boolean isGetContentResourceUsedOnPage(MethodInvocationTree tree) {
        return GET_CONTENT_RESOURCE_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean isGetContentResourceUsedOnPage(VariableTree tree) {
        return tree.initializer() instanceof MethodInvocationTree &&
            !((MethodInvocationTree) tree.initializer()).symbol().isUnknown() &&
            isPage(((MethodInvocationTree) tree.initializer()).symbol().owner().type()
                .fullyQualifiedName()) &&
            GET_CONTENT_RESOURCE_METHOD.equals(((MethodInvocationTree) tree.initializer()).symbol().name());
    }

    private boolean isGetContentResourceUsedOnResource(AssignmentExpressionTree tree) {
        return isResource(tree) &&
            tree.expression() instanceof MethodInvocationTree &&
            GET_CONTENT_RESOURCE_METHOD.equals(((MethodInvocationTree) tree.expression()).symbol().name());
    }

    private boolean isPage(String name) {
        return Constants.PAGE.equals(name);
    }

    private boolean isThisAResourceNullCheck(IfStatementTree tree, String ifType) {
        boolean result = false;
        if (ifType.equals(tree.condition().kind().name())) {
            result = isThisANullCheck(tree);
        }
        return result;
    }

    private boolean isRightSideNullCheck(IfStatementTree tree) {
        return Kind.NULL_LITERAL.equals(tree.condition().lastToken().parent().kind());
    }

    private boolean isThisANullCheck(IfStatementTree tree) {
        boolean result = false;
        if (tree.condition().firstToken().parent() instanceof IdentifierTree &&
            Constants.RESOURCE_TYPE
                .equals(((IdentifierTree) tree.condition().firstToken().parent()).symbolType()
                    .fullyQualifiedName()) &&
            Kind.NULL_LITERAL.equals(tree.condition().lastToken().parent().kind())) {
            result = true;
        } else if (tree.condition().lastToken().parent() instanceof IdentifierTree &&
            Constants.RESOURCE_TYPE
                .equals(((IdentifierTree) tree.condition().lastToken().parent()).symbolType()
                    .fullyQualifiedName()) &&
            Kind.NULL_LITERAL.equals(tree.condition().firstToken().parent().kind())) {
            result = true;
        }
        return result;
    }

    private boolean isNonNullUsed(MethodInvocationTree tree) {
        return NON_NULL_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean isAllNonNullUsed(MethodInvocationTree tree) {
        return ALL_NON_NULL_METHOD.equals(tree.methodSelect().lastToken().text());
    }

    private boolean isThisIsNullMethod(MethodInvocationTree tree) {
        return IS_NULL_METHOD.equals(tree.methodSelect().lastToken().text());
    }
}
