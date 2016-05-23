package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Rule(
	key = AdministrativeAccessUsageCheck.RULE_KEY,
	name = AdministrativeAccessUsageCheck.RULE_MESSAGE,
	priority = Priority.MAJOR,
	tags = Tags.AEM
)
public class AdministrativeAccessUsageCheck extends AbstractMethodDetection {

	public static final String RULE_KEY = "AEM-11";

	public static final String RULE_MESSAGE = "Do not use deprecated administrative access methods";

	private static final Map<String, String> SUBSTITUTES = ImmutableMap.<String, String>builder()
		.put("loginAdministrative", "loginService")
		.put("getAdministrativeResourceResolver", "getServiceResourceResolver")
		.build();

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
	}

	@Override
	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		String method = ((MemberSelectExpressionTree) mit.methodSelect()).identifier().name();
		context.reportIssue(this, mit, String.format("Method '%s' is deprecated. Use '%s' instead.", method, SUBSTITUTES.get(method)));
		super.onMethodInvocationFound(mit);
	}

	@Override
	protected List<MethodMatcher> getMethodInvocationMatchers() {
		return ImmutableList.of(
				//@formatter:off
				MethodMatcher.create()
						.typeDefinition("org.apache.sling.jcr.api.SlingRepository")
						.name("loginAdministrative")
				.addParameter("java.lang.String"),
				MethodMatcher.create()
						.typeDefinition("org.apache.sling.api.resource.ResourceResolverFactory")
						.name("getAdministrativeResourceResolver")
						.addParameter("java.util.Map")
				//@formatter:on
		);
	}
}
