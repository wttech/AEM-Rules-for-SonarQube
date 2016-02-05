package com.cognifide.aemrules.checks.resourceresolver.close;

import com.google.common.collect.Sets;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collection;
import java.util.Set;

/**
 * Finds all injector variable declarations. Used in method's bodies only. Works only for declaration within the same
 * file - api limitations: {@link Symbol#declaration()}
 */
class FindRRDeclarationVisitor extends BaseTreeVisitor {

	public static final String RESOURCE_RESOLVER_NAME = "org.apache.sling.api.resource.ResourceResolver";

	public static final String RESOURCE_RESOLVER_FACTORY = "org.apache.sling.api.resource.ResourceResolverFactory";

	private final Set<VariableTree> resourceResolvers;

	FindRRDeclarationVisitor() {
		resourceResolvers = Sets.newHashSet();
	}

	public Collection<VariableTree> getDeclarations() {
		return resourceResolvers;
	}

	@Override
	public void visitAssignmentExpression(AssignmentExpressionTree tree) {
		if (isExpressionAMethodInvocation(tree) && isVaraibleAnIdentifier(tree)) {
			final MethodInvocationTree methodInvocation = (MethodInvocationTree) tree.expression();
			final IdentifierTree identifier = (IdentifierTree) tree.variable();
			if (isManuallyCreatedResourceResolver(methodInvocation)) {
				resourceResolvers.add((VariableTree) identifier.symbol().declaration());
			} else if (isResourceResolver(methodInvocation) && methodInvocation.methodSelect().is(Kind.IDENTIFIER)) {
				MethodTree methodDeclaration = getMethodTree(methodInvocation);
				// variable 'methodDeclaration' can be null in case when method declaration isn't within the same file.
				if (methodDeclaration != null && isManuallyCreatedResourceResolver(methodDeclaration)) {
					resourceResolvers.add((VariableTree) getDeclaration(identifier));
				}
			}
		}
		super.visitAssignmentExpression(tree);
	}

	private boolean isManuallyCreatedResourceResolver(MethodTree methodDeclaration) {
		CheckIfRRCreatedManually rrCreatedManually = new CheckIfRRCreatedManually();
		methodDeclaration.accept(rrCreatedManually);
		return rrCreatedManually.isCreatedManually();
	}

	private boolean isManuallyCreatedResourceResolver(MethodInvocationTree methodInvocation) {
		return isResourceResolver(methodInvocation) && isRRF(methodInvocation);
	}

	private boolean isExpressionAMethodInvocation(AssignmentExpressionTree tree) {
		return tree.expression().is(Kind.METHOD_INVOCATION);
	}

	private boolean isVaraibleAnIdentifier(AssignmentExpressionTree tree) {
		return tree.variable().is(Kind.IDENTIFIER);
	}

	private MethodTree getMethodTree(MethodInvocationTree methodInvocation) {
		IdentifierTree method = (IdentifierTree) methodInvocation.methodSelect();
		return (MethodTree) getDeclaration(method);
	}

	/**
	 * Works for declaration within the same file only - mentioned in {@link Symbol#declaration()}
	 *
	 * @param variable identifier tree
	 * @return the Tree of the declaration of this variable. Null if declaration does not occur in the currently
	 * analyzed file.
	 */
	private Tree getDeclaration(IdentifierTree variable) {
		return variable.symbol().declaration();
	}

	@Override
	public void visitReturnStatement(ReturnStatementTree tree) {
		if (tree.expression() != null && tree.expression().is(Kind.IDENTIFIER)) {
			IdentifierTree identifier = (IdentifierTree) tree.expression();
			Tree declaration = identifier.symbol().declaration();
			if (resourceResolvers.contains(declaration)) {
				resourceResolvers.remove(declaration);
			}
		}
		super.visitReturnStatement(tree);
	}

	private boolean isRRF(MethodInvocationTree invocationTree) {
		return invocationTree.symbol().owner().type().fullyQualifiedName().equals(RESOURCE_RESOLVER_FACTORY);
	}

	private boolean isResourceResolver(MethodInvocationTree invocationTree) {
		return invocationTree.symbolType().fullyQualifiedName().equals(RESOURCE_RESOLVER_NAME);
	}

	@Override
	public void visitTryStatement(TryStatementTree tree) {
		// omit resources
		scan(tree.block());
		scan(tree.catches());
		scan(tree.finallyBlock());
	}

	private class CheckIfRRCreatedManually extends BaseTreeVisitor {

		private Tree declarationOfReturnedVariable;

		private boolean createdManually;

		@Override
		public void visitMethod(MethodTree tree) {
			FindDeclarationOfReturnedVariable visitor = new FindDeclarationOfReturnedVariable();
			tree.accept(visitor);
			declarationOfReturnedVariable = visitor.getDeclarationOfReturnedVariable();
			super.visitMethod(tree);
		}

		@Override
		public void visitAssignmentExpression(AssignmentExpressionTree tree) {
			if (isExpressionAMethodInvocation(tree) && variableIsEqualToReturnedVariableIn(tree)) {
				MethodInvocationTree methodInvocation = (MethodInvocationTree) tree.expression();
				if (isManuallyCreatedResourceResolver(methodInvocation)) {
					this.createdManually = true;
				} else {
					CheckIfRRCreatedManually rrCreatedManually = new CheckIfRRCreatedManually();
					getMethodTree(methodInvocation).accept(rrCreatedManually);
					this.createdManually = rrCreatedManually.isCreatedManually();
				}
			}
			super.visitAssignmentExpression(tree);
		}

		private boolean variableIsEqualToReturnedVariableIn(AssignmentExpressionTree tree) {
			return tree.variable().is(Kind.IDENTIFIER)
				&& getDeclaration((IdentifierTree) tree.variable()).equals(declarationOfReturnedVariable);
		}

		public boolean isCreatedManually() {
			return createdManually;
		}

	}

	private class FindDeclarationOfReturnedVariable extends BaseTreeVisitor {

		private Tree declarationOfReturnedVariable;

		@Override
		public void visitReturnStatement(ReturnStatementTree tree) {
			if (tree.expression() != null && tree.expression().is(Kind.IDENTIFIER)) {
				IdentifierTree identifier = (IdentifierTree) tree.expression();
				declarationOfReturnedVariable = getDeclaration(identifier);
			}
			super.visitReturnStatement(tree);
		}

		public Tree getDeclarationOfReturnedVariable() {
			return declarationOfReturnedVariable;
		}

	}
}
