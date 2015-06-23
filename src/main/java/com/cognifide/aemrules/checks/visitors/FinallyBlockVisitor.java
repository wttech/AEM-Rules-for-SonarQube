package com.cognifide.aemrules.checks.visitors;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.TreeVisitor;
import org.sonar.plugins.java.api.tree.TryStatementTree;

/**
 * Visits only finally blocks. Used only in methods.
 */
public class FinallyBlockVisitor extends BaseTreeVisitor {

	private final TreeVisitor visitor;

	public FinallyBlockVisitor(TreeVisitor visitor) {
		this.visitor = visitor;
	}

	@Override
	public void visitTryStatement(TryStatementTree tree) {
		if (tree.finallyBlock() != null) {
			tree.finallyBlock().accept(visitor);
		}
		super.visitTryStatement(tree);
	}
}
