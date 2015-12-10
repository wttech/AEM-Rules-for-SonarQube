package com.cognifide.aemrules.checks.visitors;

import java.util.Set;

import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;

import com.google.common.collect.Sets;

/**
 * @author Krzysztof Watral
 */
public class SessionUsageVisitor extends BaseTreeVisitor {

	private static final Set<String> SESSION_CLASSES = Sets.newHashSet();

	static {
		SESSION_CLASSES.add("javax.jcr.Session");
		SESSION_CLASSES.add("com.day.cq.wcm.api.PageManager");
		SESSION_CLASSES.add("org.apache.sling.api.SlingHttpServletRequest");
		SESSION_CLASSES.add("org.apache.sling.api.resource.ResourceResolver");
	}

	private Set<MemberSelectExpressionTree> sessionMemberSelect = Sets.newHashSet();

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
		if (!isValid(tree.expression().symbolType())) {
			returnStatementTree = tree;
		}
		super.visitReturnStatement(tree);
	}

	public Set<MemberSelectExpressionTree> getSessionMemberSelect() {
		return sessionMemberSelect;
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
