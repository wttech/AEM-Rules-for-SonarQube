package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
import com.google.common.collect.ImmutableSet;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.ast.parser.ArgumentListTreeImpl;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;
import java.util.Set;

@Rule(
		key = ModifiableValueMapUsageCheck.RULE_KEY,
		name = ModifiableValueMapUsageCheck.RULE_MESSAGE,
		priority = Priority.CRITICAL,
		tags = Tags.AEM)
public class ModifiableValueMapUsageCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-17";

	public static final String RULE_MESSAGE = "No changeable methods invoked on ModifiableValueMap";

	private static final String MODIFIABLE_VALUE_MAP_FULL_NAME = "org.apache.sling.api.resource.ModifiableValueMap";

	private static final Set<String> CHANGEABLE_METHODS = ImmutableSet.of("put", "putAll", "remove");

	private JavaFileScannerContext context;

	private boolean isModified;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitVariable(VariableTree tree) {
		if (MODIFIABLE_VALUE_MAP_FULL_NAME.equals(tree.type().symbolType().fullyQualifiedName())) {
			isModified = false;
			List<IdentifierTree> usagesOfMVM = tree.symbol().usages();
			checkIfMapVariableIsModified(usagesOfMVM);
			if (!isModified) {
				context.reportIssue(this, tree, RULE_MESSAGE);
			}
			super.visitVariable(tree);
		}
	}

	private void checkIfMapVariableIsModified(List<IdentifierTree> usagesOfMVM) {
		for (IdentifierTree MVMUsageIdentifier : usagesOfMVM) {
			Tree usageOfMVM = MVMUsageIdentifier.parent();
			if (usageOfMVM != null) {
				if (usageOfMVM.is(Tree.Kind.MEMBER_SELECT)) {
					MemberSelectExpressionTree expressionOnMVM = (MemberSelectExpressionTree) usageOfMVM;
					if (CHANGEABLE_METHODS.contains(expressionOnMVM.identifier().name())) {
						isModified = true;
						break;
					}
				} else if (usageOfMVM.is(Tree.Kind.ARGUMENTS)) {
					visitMethodWithMVM(MVMUsageIdentifier, usageOfMVM);
				}
			}
		}
	}

	private void visitMethodWithMVM(IdentifierTree modifiableValueMapUsageIdentifier, Tree usageOfMVM) {
		int argumentNumber = ((ArgumentListTreeImpl) usageOfMVM).indexOf(modifiableValueMapUsageIdentifier);
		MethodInvocationTree methodInvocationWithMVM = (MethodInvocationTree) usageOfMVM.parent();
		if (methodInvocationWithMVM != null) {
			MethodTree methodWithMVM = (MethodTree) methodInvocationWithMVM.symbol().declaration();
			if (methodWithMVM != null && methodWithMVM.is(Tree.Kind.METHOD)) {
				MethodWithMVMVisitor methodWithMVMVisitor = new MethodWithMVMVisitor(this, argumentNumber);
				methodWithMVM.accept(methodWithMVMVisitor);
			}
		}
	}

	private class MethodWithMVMVisitor extends BaseTreeVisitor {

		private final ModifiableValueMapUsageCheck scanner;

		private final int argumentIndex;


		public MethodWithMVMVisitor(ModifiableValueMapUsageCheck scanner, int argumentIndex) {
			this.scanner = scanner;
			this.argumentIndex = argumentIndex;
		}

		@Override
		public void visitMethod(MethodTree tree) {
			List<VariableTree> parameters = tree.parameters();
			if (argumentIndex >= 0 && argumentIndex <= (parameters.size() - 1)) {
				VariableTree variableTree = parameters.get(argumentIndex);
				scanner.checkIfMapVariableIsModified(variableTree.symbol().usages());
			}
			super.visitMethod(tree);
		}

	}
}