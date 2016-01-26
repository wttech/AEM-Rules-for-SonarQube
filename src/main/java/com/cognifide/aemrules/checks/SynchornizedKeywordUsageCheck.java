package com.cognifide.aemrules.checks;

import static com.cognifide.aemrules.checks.PreferSlingServletAnnotation.NAME;
import static com.cognifide.aemrules.checks.SynchornizedKeywordUsageCheck.DESCRIPTION;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.checks.SubscriptionBaseVisitor;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.ModifierKeywordTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
	key = SynchornizedKeywordUsageCheck.RULE_KEY,
	name = NAME,
	description = DESCRIPTION,
	priority = Priority.INFO,
	tags = {Tag.MULTI_THREADING, Tag.PERFORMANCE})
public class SynchornizedKeywordUsageCheck extends SubscriptionBaseVisitor {

	private static final String MESSAGE = "Usage of 'synchronized' keyword should be avoided if possible.";

	static final String RULE_KEY = "AEM-15";

	static final String NAME = "'synchronaized' keyword was used";

	static final String DESCRIPTION = "Check if using 'synchronized' can be replaced with more sophisticated solution.";

	private static final List<Tree.Kind> ACCEPTED_NODE_KIDNS = 
		ImmutableList.of(Kind.SYNCHRONIZED_STATEMENT, Kind.METHOD);
	
	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ACCEPTED_NODE_KIDNS;
	}

	@Override
	public void visitNode(Tree tree) {
		switch (tree.kind()) {
			case METHOD:
				SynchronizedMethodVisitor visitor = new SynchronizedMethodVisitor(this);
				tree.accept(visitor);
				break;
			case SYNCHRONIZED_STATEMENT:
				addIssue(tree, MESSAGE);
				break;
		}
		super.visitNode(tree);
	}

	private static class SynchronizedMethodVisitor extends BaseTreeVisitor {

		private final SubscriptionBaseVisitor visitor;

		SynchronizedMethodVisitor(SubscriptionBaseVisitor visitor) {
			this.visitor = visitor;
		}

		@Override
		public void visitMethod(MethodTree tree) {
			List<ModifierKeywordTree> modifiers = tree.modifiers().modifiers();
			for (ModifierKeywordTree modifier : modifiers) {
				if (modifier.modifier() == Modifier.SYNCHRONIZED) {
					visitor.addIssue(tree, MESSAGE);
				}
			}
			super.visitMethod(tree);
		}
	}

}
