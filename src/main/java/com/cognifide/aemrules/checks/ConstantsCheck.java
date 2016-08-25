package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.util.ConstantsChecker;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

@Rule(
		key = ConstantsCheck.RULE_KEY,
		name = ConstantsCheck.RULE_MESSAGE,
		priority = Priority.MINOR,
		tags = Tags.AEM
)
public class ConstantsCheck extends IssuableSubscriptionVisitor {

	protected static final String RULE_KEY = "AEM-2";

	public static final String RULE_MESSAGE = "Use predefined constant instead of hardcoded value.";

	@Override
	public List<Kind> nodesToVisit() {
		return Lists.newArrayList(Kind.STRING_LITERAL);
	}

	@Override
	public void visitNode(Tree stringLiteral) {
		String literalValue = removeQuotes(((LiteralTree) stringLiteral).value());
		if (ConstantsChecker.isConstant(literalValue)) {
			String constantFieldName = ConstantsChecker.getConstantFieldName(literalValue);
			reportIssue(stringLiteral, String.format("%s (%s)", RULE_MESSAGE, constantFieldName));
		}
		super.visitNode(stringLiteral);
	}

	private String removeQuotes(String value) {
		return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
	}


}
