package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.ModifierKeywordTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

import static com.cognifide.aemrules.checks.SynchornizedKeywordUsageCheck.DESCRIPTION;

@Rule(
	key = SynchornizedKeywordUsageCheck.RULE_KEY,
	name = SynchornizedKeywordUsageCheck.MESSAGE,
	description = DESCRIPTION,
	priority = Priority.INFO,
	tags = {Tags.MULTI_THREADING, Tags.PERFORMANCE})
public class SynchornizedKeywordUsageCheck extends IssuableSubscriptionVisitor {

	protected static final String MESSAGE = "Usage of 'synchronized' keyword should be avoided if possible.";

	protected static final String RULE_KEY = "AEM-15";

	protected static final String DESCRIPTION = "Check if using 'synchronized' can be replaced with more sophisticated solution.";

	private static final List<Tree.Kind> ACCEPTED_NODE_KINDS
		= ImmutableList.of(Kind.SYNCHRONIZED_STATEMENT, Kind.METHOD);

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ACCEPTED_NODE_KINDS;
	}

	@Override
	public void visitNode(Tree tree) {
		switch (tree.kind()) {
			case METHOD:
				SynchronizedMethodVisitor visitor = new SynchronizedMethodVisitor(this);
				tree.accept(visitor);
				break;
			case SYNCHRONIZED_STATEMENT:
				reportIssue(tree, MESSAGE);
				break;
		}
		super.visitNode(tree);
	}

	private static class SynchronizedMethodVisitor extends BaseTreeVisitor {

		private final IssuableSubscriptionVisitor visitor;

		SynchronizedMethodVisitor(IssuableSubscriptionVisitor visitor) {
			this.visitor = visitor;
		}

		@Override
		public void visitMethod(MethodTree tree) {
			List<ModifierKeywordTree> modifiers = tree.modifiers().modifiers();
			for (ModifierKeywordTree modifier : modifiers) {
				if (modifier.modifier() == Modifier.SYNCHRONIZED) {
					visitor.reportIssue(modifier, MESSAGE);
				}
			}
			super.visitMethod(tree);
		}
	}

}
