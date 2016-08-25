package com.cognifide.aemrules.checks.visitors;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;

import java.util.Set;

/**
 * Checks if variable is being closed. Used only in finally blocks.
 */
public class CheckClosedVisitor extends BaseTreeVisitor {

	private static final String CLOSE_METHOD_NAME = "close";

	private final Set<IdentifierTree> usages;

	private boolean closed;

	public CheckClosedVisitor(Set<IdentifierTree> usages) {
		this.usages = usages;
		this.closed = false;
	}

	public boolean isClosed() {
		return closed;
	}

	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		if (usages.contains(tree.expression())) {
			closed |= tree.identifier().name().equals(CLOSE_METHOD_NAME);
		}
		super.visitMemberSelectExpression(tree);
	}

}
