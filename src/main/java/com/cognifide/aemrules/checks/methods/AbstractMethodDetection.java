/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.checks.methods;

import java.util.List;

import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.collect.ImmutableList;

public abstract class AbstractMethodDetection extends IssuableSubscriptionVisitor {

	private List<MethodMatcher> matchers;

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.METHOD_INVOCATION, Tree.Kind.NEW_CLASS);
	}

	@Override
	public void visitNode(Tree tree) {
		if (hasSemantic()) {
			for (MethodMatcher invocationMatcher : matchers()) {
				checkInvocation(tree, invocationMatcher);
			}
		}
	}

	private void checkInvocation(Tree tree, MethodMatcher invocationMatcher) {
		if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
			MethodInvocationTree mit = (MethodInvocationTree) tree;
			if (invocationMatcher.matches(mit)) {
				onMethodInvocationFound(mit);
			}
		} else if (tree.is(Tree.Kind.NEW_CLASS)) {
			NewClassTree newClassTree = (NewClassTree) tree;
			if (invocationMatcher.matches(newClassTree)) {
				onConstructorFound(newClassTree);
			}
		}
	}

	protected abstract List<MethodMatcher> getMethodInvocationMatchers();

	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		// Do nothing by default
	}

	protected void onConstructorFound(NewClassTree newClassTree) {
		// Do nothing by default
	}

	private List<MethodMatcher> matchers() {
		if (matchers == null) {
			matchers = getMethodInvocationMatchers();
		}
		return matchers;
	}
}