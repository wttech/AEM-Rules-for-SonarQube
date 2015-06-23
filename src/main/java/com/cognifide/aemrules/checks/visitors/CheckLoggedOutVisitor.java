package com.cognifide.aemrules.checks.visitors;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;

import java.util.List;

/**
 * Checks if variable is being logged out. Used only in finally blocks.
 */
public class CheckLoggedOutVisitor extends BaseTreeVisitor {

	private static final String LOG_OUT_METHOD_NAME = "logout";

	private final List<IdentifierTree> usages;

	private boolean loggedOut;

	public CheckLoggedOutVisitor(List<IdentifierTree> usages) {
		this.usages = usages;
		this.loggedOut = false;
	}

	public boolean isLoggedOut() {
		return loggedOut;
	}

	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		if (usages.contains(tree.expression())) {
			loggedOut |= tree.identifier().name().equals(LOG_OUT_METHOD_NAME);
		}
		super.visitMemberSelectExpression(tree);
	}

}
