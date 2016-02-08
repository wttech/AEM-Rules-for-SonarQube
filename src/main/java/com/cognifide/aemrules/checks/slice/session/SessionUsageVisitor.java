package com.cognifide.aemrules.checks.slice.session;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;

import com.google.common.collect.Sets;
import java.util.Collections;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
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

	private final Set<MemberSelectExpressionTree> sessionMemberSelect = Sets.newHashSet();

	private ReturnStatementTree returnStatementTree;

	public Set<MemberSelectExpressionTree> getSessionMemberSelect() {
		return Collections.unmodifiableSet(sessionMemberSelect);
	}

	public ReturnStatementTree getReturnStatementTree() {
		return returnStatementTree;
	}

	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		if (isSubtypeOfSessionClass(tree.expression())) {
			sessionMemberSelect.add(tree);
		}
		super.visitMemberSelectExpression(tree);
	}

	@Override
	public void visitReturnStatement(ReturnStatementTree tree) {
		ExpressionTree expression = tree.expression();
		if (isNotNullLiteral(expression) && isSubtypeOfSessionClass(expression)) {
			returnStatementTree = tree;
		}
		super.visitReturnStatement(tree);
	}

	@Override
	public void visitMethodInvocation(MethodInvocationTree tree) {
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
