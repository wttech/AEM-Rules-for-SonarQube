package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
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
		key = HttpConstantCheck.RULE_KEY,
		name = HttpConstantCheck.RULE_MESSAGE,
		priority = Priority.MINOR,
		tags = Tags.AEM
)
public class HttpConstantCheck extends IssuableSubscriptionVisitor {

	protected static final String RULE_KEY = "AEM-14";

	private static final String HTTP_LITERAL = "http";

	public static final String RULE_MESSAGE = "Using http literal hardcoded makes it difficult to switch to https later on.";

	@Override
	public List<Kind> nodesToVisit() {
		return Lists.newArrayList(Kind.STRING_LITERAL);
	}

	@Override
	public void visitNode(Tree stringLiteral) {
		String literalValue = removeQuotes(((LiteralTree) stringLiteral).value());
		if (HTTP_LITERAL.equalsIgnoreCase(literalValue)) {
			reportIssue(stringLiteral, RULE_MESSAGE);
		}
		super.visitNode(stringLiteral);
	}

	private String removeQuotes(String value) {
		return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
	}


}
