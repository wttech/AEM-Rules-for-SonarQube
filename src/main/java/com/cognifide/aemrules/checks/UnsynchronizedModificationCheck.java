package com.cognifide.aemrules.checks;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.checks.SubscriptionBaseVisitor;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.cognifide.aemrules.checks.visitors.AnnotationsVisitor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Rule(
		key = "AEM-13",
		name = "Modification of a Service fields should be synchronized.",
		priority = Priority.CRITICAL,
		tags = {Tag.BUG, Tag.MULTI_THREADING}
)
public class UnsynchronizedModificationCheck extends SubscriptionBaseVisitor {

	private final Deque<Boolean> inSynchronizedBlock = Lists.newLinkedList();

	private final Set<Symbol> fields = Sets.newHashSet();

	private boolean service;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		inSynchronizedBlock.push(false);
		service = true;
		super.scanFile(context);
		inSynchronizedBlock.clear();
	}

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.CLASS, Tree.Kind.SYNCHRONIZED_STATEMENT, Tree.Kind.METHOD, Tree.Kind.EXPRESSION_STATEMENT);
	}

	@Override
	public void visitNode(Tree tree) {
		if (!service) {
			return;
		}
		switch (tree.kind()) {
			case CLASS:
				getClassFields((ClassTree) tree);
				break;
			case METHOD:
				inSynchronizedBlock.push(checkSynchronizedOrActivate((MethodTree) tree));
				break;
			case SYNCHRONIZED_STATEMENT:
				inSynchronizedBlock.push(true);
				break;
			case EXPRESSION_STATEMENT:
				checkExpression((ExpressionStatementTree) tree);
				break;
			default:
				break;
		}
	}

	private void getClassFields(ClassTree tree) {
		AnnotationsVisitor annotationsVisitor = new AnnotationsVisitor("org.apache.felix.scr.annotations.Service");
		tree.accept(annotationsVisitor);
		service = annotationsVisitor.isAnnotatedWithAny();
		if (!service) {
			return;
		}
		for (Tree member : tree.members()) {
			if (member.is(Tree.Kind.VARIABLE)) {
				fields.add(((VariableTree) member).symbol());
			}
		}
	}

	/*
	 * check if method is synchronized or annotated with org.apache.felix.scr.annotations.Activate or org.apache.felix.scr.annotations.Deactivate
	 */
	private boolean checkSynchronizedOrActivate(MethodTree tree) {
		AnnotationsVisitor annotationsVisitor = new AnnotationsVisitor("org.apache.felix.scr.annotations.Activate", "org.apache.felix.scr.annotations.Deactivate");
		tree.accept(annotationsVisitor);
		return ModifiersUtils.hasModifier((tree).modifiers(), Modifier.SYNCHRONIZED) || annotationsVisitor.isAnnotatedWithAny();
	}

	private void checkExpression(ExpressionStatementTree tree) {
		ExpressionTree expression = tree.expression();
		if (!isInSyncBlock() && expression.is(Tree.Kind.ASSIGNMENT)) {
			checkAssignment(((AssignmentExpressionTree) expression).variable(), tree);
		}
	}

	private void checkAssignment(ExpressionTree variable, ExpressionStatementTree statementTree) {
		if (variable.is(Tree.Kind.IDENTIFIER)) {
			Symbol variableSymbol = ((IdentifierTree) variable).symbol();

			if (fields.contains(variableSymbol)) {
				addIssue(statementTree, "Unsynchronized modification of a Service field is not thread safe.");
			}
		} else if (variable.is(Tree.Kind.MEMBER_SELECT)) {
			MemberSelectExpressionTree mse = (MemberSelectExpressionTree) variable;
			checkAssignment(mse.identifier(), statementTree);
		}
	}

	public boolean isInSyncBlock() {
		return inSynchronizedBlock.peek();
	}

	@Override
	public void leaveNode(Tree tree) {
		if (tree.is(Tree.Kind.METHOD, Tree.Kind.SYNCHRONIZED_STATEMENT)) {
			inSynchronizedBlock.pop();
		}
	}

}
