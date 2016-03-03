package com.cognifide.aemrules.checks.slice.session;

import static com.cognifide.aemrules.checks.slice.session.ModelsShouldNotUseSessionCheck.RULE_MESSAGE;
import com.google.common.collect.ImmutableSet;
import java.util.Set;

import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;

import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

/**
 * @author Krzysztof Watral
 */
class SessionUsageVisitor extends BaseTreeVisitor {

	private static final Set<String> SESSION_CLASSES = ImmutableSet.of(
		"javax.jcr.Session",
		"com.day.cq.wcm.api.PageManager",
		"org.apache.sling.api.SlingHttpServletRequest",
		"org.apache.sling.api.resource.ResourceResolver");

	private final JavaFileScanner javaFileScanner;

	private final JavaFileScannerContext context;

	SessionUsageVisitor(JavaFileScanner javaFileScanner, JavaFileScannerContext context) {
		this.javaFileScanner = javaFileScanner;
		this.context = context;
	}

	@Override
	public void visitNewClass(NewClassTree tree) {
		// ignore anonymous classes here
	}

	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		if (isSubtypeOfSessionClass(tree.expression())) {
			context.reportIssue(javaFileScanner, tree, RULE_MESSAGE);
		}
		super.visitMemberSelectExpression(tree);
	}

	@Override
	public void visitReturnStatement(ReturnStatementTree tree) {
		ExpressionTree expression = tree.expression();
		if (isNotNullLiteral(expression) && isSubtypeOfSessionClass(expression)) {
			context.reportIssue(javaFileScanner, tree, RULE_MESSAGE);
		}
		super.visitReturnStatement(tree);
	}

	private boolean isNotNullLiteral(ExpressionTree expression) {
		return null != expression && !expression.is(Kind.NULL_LITERAL);
	}

	private boolean isSubtypeOfSessionClass(ExpressionTree expression) {
		boolean isSubtype = false;
		if (null != expression) {
			final Type type = expression.symbolType();
			for (String sessionClass : SESSION_CLASSES) {
				if (type.isSubtypeOf(sessionClass)) {
					isSubtype = true;
					break;
				}
			}
		}
		return isSubtype;
	}
}
