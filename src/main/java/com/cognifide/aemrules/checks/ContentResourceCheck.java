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
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(
    key = ContentResourceCheck.RULE_KEY,
    name = ContentResourceCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
public class ContentResourceCheck extends BaseTreeVisitor implements JavaFileScanner {

  public static final String RULE_KEY = "AEM-18";

  public static final String RULE_MESSAGE = "Always null check the returned value of Page.getContentResource() method";

  private JavaFileScannerContext context;

  private boolean isNullChecked = false;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitMethodInvocation(MethodInvocationTree tree) {
    if (tree.symbol().owner().name().equals("Resource") && !isNullChecked) {
      context.reportIssue(this, tree, RULE_MESSAGE);
    }
    super.visitMethodInvocation(tree);
  }

  @Override
  public void visitBinaryExpression(BinaryExpressionTree tree) {
    isNullChecked = false;
    isNullChecked = hasResourceComparison(tree);
    super.visitBinaryExpression(tree);
  }

  private boolean hasResourceComparison(BinaryExpressionTree tree) {
    return isResourceNullCheck(tree.leftOperand()) || isResourceNullCheck(tree.rightOperand());
  }

  private boolean isResourceNullCheck(ExpressionTree operand) {
    return operand.is(Tree.Kind.NULL_LITERAL) && operand.symbolType()
        .isSubtypeOf(Constants.RESOURCE_TYPE);
  }
}