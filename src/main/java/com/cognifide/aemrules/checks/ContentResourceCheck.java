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
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = ContentResourceCheck.RULE_KEY,
    name = ContentResourceCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
public class ContentResourceCheck extends BaseTreeVisitor implements JavaFileScanner {

  public static final String RULE_KEY = "AEM-18";

  public static final String RULE_MESSAGE = "Always null check the returned value of Page.getContentResource() method";

  public static final String METHOD_NAME = "getContentResource";

  private JavaFileScannerContext context;

  private Map<String, Boolean> contentResources = new HashMap();

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitBinaryExpression(BinaryExpressionTree tree) {
    if (isResourceNullChecked(tree.leftOperand(), tree.rightOperand())) {
      contentResources.replace(tree.leftOperand().firstToken().text(), true);
    } else if (isResourceNullChecked(tree.rightOperand(), tree.leftOperand())) {
      contentResources.replace(tree.rightOperand().firstToken().text(), true);
    }
    super.visitBinaryExpression(tree);
  }

  @Override
  public void visitAssignmentExpression(AssignmentExpressionTree tree) {
    if (Constants.RESOURCE_TYPE.equals(tree.variable().symbolType().fullyQualifiedName())) {
      contentResources.put(tree.variable().firstToken().text(), false);
    }
    super.visitAssignmentExpression(tree);
  }


  @Override
  public void visitMethodInvocation(MethodInvocationTree tree) {
    if (contentResources.containsKey(tree.firstToken().text())) {
      if (!contentResources.getOrDefault(tree.firstToken().text(), false)) {
        context.reportIssue(this, tree, RULE_MESSAGE);
      }
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

  private void shouldBeNullChecked(VariableTree tree) {
    if (isPage(
        ((MethodInvocationTree) tree.initializer()).symbol().owner().type().fullyQualifiedName())
        && METHOD_NAME.equals(((MethodInvocationTree) tree.initializer()).symbol().name())) {
      contentResources.put(tree.simpleName().name(), false);
    }
  }

  private void chainedMethodsNullCheck(MethodInvocationTree tree) {
    if (tree.methodSelect() instanceof MemberSelectExpressionTree) {
      MemberSelectExpressionTree expressionTree = (MemberSelectExpressionTree) tree.methodSelect();
      if (expressionTree.expression() instanceof MethodInvocationTree) {
        MethodInvocationTree invocationTree = (MethodInvocationTree) expressionTree.expression();
        if (isPage(invocationTree.symbol().owner().type().fullyQualifiedName())
            && isGetContentResourceUsed(invocationTree)) {
          context.reportIssue(this, tree, RULE_MESSAGE);
        }
      }
    }
  }

  private boolean isResourceNullChecked(ExpressionTree leftOperand, ExpressionTree rightOperand) {
    return leftOperand.symbolType().isSubtypeOf(Constants.RESOURCE_TYPE) && rightOperand
        .is(Kind.NULL_LITERAL);
  }

  private boolean isResourceInitialized(VariableTree tree) {
    return tree.initializer() != null && tree.initializer().kind() != Kind.NULL_LITERAL;
  }

  private boolean isGetContentResourceUsed(MethodInvocationTree tree) {
    return METHOD_NAME.equals(tree.methodSelect().lastToken().text());
  }

  private boolean isPage(String name) {
    return Constants.PAGE.equals(name);
  }
}