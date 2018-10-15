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

  public static final String METHOD_NAME = "getContentResource";

  public static final String NOT_EQUAL = "NOT_EQUAL_TO";

  public static final String EQUAL = "EQUAL_TO";

  private boolean returnOccurredInsideEqualNullCheck = false;

  private boolean insideEqualNullCheck = false;

  private JavaFileScannerContext context;

  private Map<String, Boolean> contentResources = new HashMap();

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitIfStatement(IfStatementTree tree) {
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
    if (!contentResources.getOrDefault(tree.firstToken().text(), true)
        && !returnOccurredInsideEqualNullCheck
    ) {
      context.reportIssue(this, tree, RULE_MESSAGE);
    }
    chainedMethodsNullCheck(tree);
    super.visitMethodInvocation(tree);
  }

  @Override
  public void visitVariable(VariableTree tree) {
    if (isResourceInitialized(tree)) {
      shouldBeNullChecked(tree);
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

  private void shouldBeNullChecked(VariableTree tree) {
    if (tree.initializer() instanceof MethodInvocationTree &&
        !((MethodInvocationTree) tree.initializer()).symbol().isUnknown() &&
        isPage(((MethodInvocationTree) tree.initializer()).symbol().owner().type()
            .fullyQualifiedName()) &&
        METHOD_NAME.equals(((MethodInvocationTree) tree.initializer()).symbol().name())) {
      contentResources.put(tree.simpleName().name(), false);
    }
  }

  private void chainedMethodsNullCheck(MethodInvocationTree tree) {
    if (tree.methodSelect() instanceof MemberSelectExpressionTree) {
      MemberSelectExpressionTree expressionTree = (MemberSelectExpressionTree) tree.methodSelect();
      if (expressionTree.expression() instanceof MethodInvocationTree) {
        MethodInvocationTree invocationTree = (MethodInvocationTree) expressionTree.expression();
        if (!invocationTree.symbol().isUnknown() &&
            isPage(invocationTree.symbol().owner().type().fullyQualifiedName()) &&
            isGetContentResourceUsedOnPage(invocationTree) && !returnOccurredInsideEqualNullCheck
        ) {
          context.reportIssue(this, tree, RULE_MESSAGE);
        }
      }
    }
  }

  private void cleanFlagsAfterIfStatement(IfStatementTree tree) {
    updateNullCheckedResources(tree, false);
    insideEqualNullCheck = false;
  }

  private void updateNullCheckedResources(IfStatementTree tree, boolean value) {
    if (isRightSideNullCheck(tree)) {
      contentResources.replace(tree.condition().firstToken().text(), value);
    } else {
      contentResources.replace(tree.condition().lastToken().text(), value);
    }
  }

  private boolean isResource(AssignmentExpressionTree tree) {
    return Constants.RESOURCE_TYPE.equals(tree.variable().symbolType().fullyQualifiedName());
  }

  private boolean isResourceInitialized(VariableTree tree) {
    return tree.initializer() != null && tree.initializer().kind() != Kind.NULL_LITERAL;
  }

  private boolean isGetContentResourceUsedOnPage(MethodInvocationTree tree) {
    return METHOD_NAME.equals(tree.methodSelect().lastToken().text());
  }

  private boolean isGetContentResourceUsedOnResource(AssignmentExpressionTree tree) {
    return isResource(tree) &&
        tree.expression() instanceof MethodInvocationTree &&
        METHOD_NAME.equals(((MethodInvocationTree) tree.expression()).symbol().name());
  }

  private boolean isPage(String name) {
    return Constants.PAGE.equals(name);
  }

  public boolean isThisAResourceNullCheck(IfStatementTree tree, String ifType) {
    if (ifType.equals(tree.condition().kind().name())) {
      if (tree.condition().firstToken().parent() instanceof IdentifierTree
          && Constants.RESOURCE_TYPE.equals(
          ((IdentifierTree) tree.condition().firstToken().parent()).symbolType()
              .fullyQualifiedName()) && Kind.NULL_LITERAL
          .equals(tree.condition().lastToken().parent().kind())) {
        return true;
      } else if (tree.condition().lastToken().parent() instanceof IdentifierTree
          && Constants.RESOURCE_TYPE.equals(
          ((IdentifierTree) tree.condition().lastToken().parent()).symbolType()
              .fullyQualifiedName()) && Kind.NULL_LITERAL
          .equals(tree.condition().firstToken().parent().kind())) {
        return true;
      }
    }
    return false;
  }

  private boolean isRightSideNullCheck(IfStatementTree tree) {
    return Kind.NULL_LITERAL
        .equals(tree.condition().lastToken().parent().kind());
  }
}