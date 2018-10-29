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
package com.cognifide.aemrules.java.checks;

import static org.sonar.plugins.java.api.tree.Tree.Kind.EQUAL_TO;
import static org.sonar.plugins.java.api.tree.Tree.Kind.NOT_EQUAL_TO;

import com.cognifide.aemrules.java.Constants;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
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
import org.sonar.plugins.java.api.tree.SyntaxToken;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = ContentResourceShouldBeNullCheckedCheck.RULE_KEY,
    name = ContentResourceShouldBeNullCheckedCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "5min"
)
public class ContentResourceShouldBeNullCheckedCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-18";

    public static final String RULE_MESSAGE = "Always null check the returned value of Page.getContentResource() method";

    private static final String WCM_API_PAGE_QUALIFIED_NAME = "com.day.cq.wcm.api.Page";

    private static final String GET_CONTENT_RESOURCE_METHOD = "getContentResource";

    private static final String NON_NULL_METHOD = "nonNull";

    private static final String ALL_NON_NULL_METHOD = "allNotNull";

    private static final String IS_NULL_METHOD = "isNull";

    private static final int METHOD_FIRST_ARGUMENT = 0;

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
        if (isResourceNullCheckOfType(tree, NOT_EQUAL_TO.name())) {
            updateNullCheckedResources(tree, true);
        } else if (isResourceNullCheckOfType(tree, EQUAL_TO.name())) {
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
        if (isResourceInitialized(tree) && isGetContentResourceUsedOnPage(tree)) {
            contentResources.put(tree.simpleName().name(), false);
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
        boolean rightOperandIsNull = Kind.NULL_LITERAL.equals(tree.condition().lastToken().parent().kind());
        SyntaxToken variable = rightOperandIsNull ? tree.condition().firstToken() : tree.condition().lastToken();
        contentResources.replace(variable.text(), value);
    }

    private void externalLibraryNullChecks(MethodInvocationTree tree) {
        if (isNonNullUsed(tree)) {
            contentResources.replace(tree.arguments().get(METHOD_FIRST_ARGUMENT).toString(), true);
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
        return Constants.SLING_RESOURCE_QUALIFIED_NAME.equals(tree.variable().symbolType().fullyQualifiedName());
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
        return WCM_API_PAGE_QUALIFIED_NAME.equals(name);
    }

    private boolean isResourceNullCheckOfType(IfStatementTree tree, String ifType) {
        boolean result = false;
        if (ifType.equals(tree.condition().kind().name())) {
            result = isResourceNullCheck(tree);
        }
        return result;
    }

    private boolean isResourceNullCheck(IfStatementTree tree) {
        SyntaxToken leftOperand = tree.condition().firstToken();
        SyntaxToken rightOperand = tree.condition().lastToken();

        if (leftOperand == null || rightOperand == null) {
            return false;
        }

        return isResourceNullCheck(leftOperand, rightOperand) || isResourceNullCheck(rightOperand, leftOperand);
    }

    private boolean isResourceNullCheck(SyntaxToken operandA, SyntaxToken operandB) {
        return isResourceType(operandA.parent()) && isNullLiteral(operandB.parent());
    }

    private boolean isNullLiteral(Tree operand) {
        return Kind.NULL_LITERAL.equals(operand.kind());
    }

    private boolean isResourceType(Tree operand) {
        return operand instanceof IdentifierTree && Constants.SLING_RESOURCE_QUALIFIED_NAME
            .equals(((IdentifierTree) operand).symbolType().fullyQualifiedName());
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
