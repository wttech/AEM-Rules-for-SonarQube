package com.cognifide.aemrules.checks.visitors;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;

import com.google.common.collect.Sets;
import java.util.Collections;
import org.sonar.plugins.java.api.tree.ExpressionTree;

/**
 * @author Krzysztof Watral
 */
public class SessionUsageVisitor extends BaseTreeVisitor {

	private static final Set<String> SESSION_CLASSES = ImmutableSet.of(
		"javax.jcr.Session",
		"com.day.cq.wcm.api.PageManager",
		"org.apache.sling.api.SlingHttpServletRequest",
		"org.apache.sling.api.resource.ResourceResolver");

	private final Set<MemberSelectExpressionTree> sessionMemberSelect = Sets.newHashSet();

	private ReturnStatementTree returnStatementTree;

	@Override
	public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
		if (!isValid(tree.expression().symbolType())) {
			sessionMemberSelect.add(tree);
		}
		super.visitMemberSelectExpression(tree);
	}

	@Override
	public void visitReturnStatement(ReturnStatementTree tree) {
		ExpressionTree expression = tree.expression();
		if (null != expression && !isValid(expression.symbolType())) {
			returnStatementTree = tree;
		}
		super.visitReturnStatement(tree);
	}

	public Set<MemberSelectExpressionTree> getSessionMemberSelect() {
		return Collections.unmodifiableSet(sessionMemberSelect);
	}

	public ReturnStatementTree getReturnStatementTree() {
		return returnStatementTree;
	}

	private boolean isValid(Type type) {
		for (String sessionClass : SESSION_CLASSES) {
			if (type.isSubtypeOf(sessionClass)) {
				return false;
			}
		}
		return true;
	}
}
