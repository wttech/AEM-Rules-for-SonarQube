package com.cognifide.aemrules.checks.visitors;

import java.util.List;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.google.common.collect.Lists;

/**
 * Finds all injector variable declarations. Used in method's bodies only.
 */
public class FindVariableDeclarationVisitor extends BaseTreeVisitor {

	private final List<VariableTree> variables;

	private final String variableTypeName;

	public FindVariableDeclarationVisitor(String variableTypeName) {
		this.variableTypeName = variableTypeName;
		variables = Lists.newArrayList();
	}

	public List<VariableTree> getDeclarations() {
		return variables;
	}

	@Override
	public void visitVariable(VariableTree variable) {
		if (isInjector(variable)) {
			variables.add(variable);
		}
		super.visitVariable(variable);
	}

	private boolean isInjector(VariableTree tree) {
		return tree.type().symbolType().fullyQualifiedName().equals(variableTypeName);
	}

	@Override
	public void visitMethod(MethodTree tree) {
		// omit all apart from block
		scan(tree.block());
	}

	@Override
	public void visitTryStatement(TryStatementTree tree) {
		// omit resources
		scan(tree.block());
		scan(tree.catches());
		scan(tree.finallyBlock());
	}
}
