package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.Constants;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

public class ReturnOccurrenceCheck extends BaseTreeVisitor {

  private boolean returnOccurred = false;

  private static final String EQUALS = "EQUAL_TO";

  public ReturnOccurrenceCheck(IfStatementTree tree) {
    visitIfStatement(tree);
  }

  public void visitIfStatement(IfStatementTree tree) {
    returnOccurred = equalsNullCheck(tree);
    super.visitIfStatement(tree);
  }

  private boolean equalsNullCheck(IfStatementTree tree) {
    if (EQUALS.equals(tree.condition().kind().name())) {
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

  public boolean returnOccurred() {
    return returnOccurred;
  }
}
