package com.cognifide.aemrules.checks.slice.iterator;

import com.cognifide.aemrules.tag.Tags;
import static org.sonar.plugins.java.api.tree.Tree.Kind.IDENTIFIER;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.DoWhileStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;

@Rule(
	key = IteratingResourcesCheck.RULE_KEY,
	name = IteratingResourcesCheck.RULE_MESSAGE,
	priority = Priority.MINOR,
	tags = Tags.AEM
)
public class IteratingResourcesCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-10";

	public static final String RULE_MESSAGE = "Use ModelProvider#getListFromResources instead of iteration";

	static final String RESOURCE_TYPE = "org.apache.sling.api.resource.Resource";

	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitWhileStatement(WhileStatementTree tree) {
		checkModelProviderInLoop(tree, tree.condition(), tree.statement());
		super.visitWhileStatement(tree);
	}

	@Override
	public void visitDoWhileStatement(DoWhileStatementTree tree) {
		if (tree.condition() instanceof BinaryExpressionTree) {
			DoWhileBinaryExpressionVisitor visitor = new DoWhileBinaryExpressionVisitor();
			tree.condition().accept(visitor);
			if (visitor.containsResourceComparison()) {
				checkModelProviderCall(tree, tree.statement());
			}
		}
		super.visitDoWhileStatement(tree);
	}

	@Override
	public void visitForStatement(ForStatementTree tree) {
		checkModelProviderInLoop(tree, tree.condition(), tree.statement());
		super.visitForStatement(tree);
	}

	@Override
	public void visitForEachStatement(ForEachStatement tree) {
		ExpressionTree expression = tree.expression();
		if (expression instanceof IdentifierTree && isResourceIterator((IdentifierTree) expression)) {
			checkModelProviderCall(tree, tree.statement());
		}
		super.visitForEachStatement(tree);
	}

	private void checkModelProviderInLoop(Tree tree, ExpressionTree condition, StatementTree statement) {
		if (isIteratingOverResources(condition)) {
			checkModelProviderCall(tree, statement);
		}
	}

	private void checkModelProviderCall(Tree tree, StatementTree statement) {
		MethodInvocationTreeVisitor methodInvocationTreeVisitor = new MethodInvocationTreeVisitor();
		statement.accept(methodInvocationTreeVisitor);
		if (methodInvocationTreeVisitor.isModelProviderGetCalled()) {
			context.reportIssue(this, tree, RULE_MESSAGE);
		}
	}

	private boolean isIteratingOverResources(ExpressionTree condition) {
		if (!(condition instanceof MethodInvocationTree)) {
			return false;
		}
		boolean resourceIterator = false;
		MethodInvocationTree methodInvocationTree = (MethodInvocationTree) condition;
		boolean subtypeOfIterator = methodInvocationTree.symbol().owner().type().isSubtypeOf("java.util.Iterator");
		if (subtypeOfIterator) {
			ExpressionTree expression = ((MemberSelectExpressionTree) methodInvocationTree.methodSelect()).expression();
			if (expression.is(IDENTIFIER)) {
				resourceIterator = isResourceIterator((IdentifierTree) expression);
			}
		}
		return subtypeOfIterator && resourceIterator;
	}

	private boolean isResourceIterator(IdentifierTree expression) {
		boolean result = false;
		Tree declaration = expression.symbol().declaration();
		if (null != declaration) {
			VariableTreeVisitor treeVisitor = new VariableTreeVisitor();
			declaration.accept(treeVisitor);
			result = treeVisitor.hasResourceTypeVariable();
		}
		return result;
	}

}
