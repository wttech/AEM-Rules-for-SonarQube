/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2018 Cognifide Limited
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
package com.cognifide.aemrules.java.checks;

import com.cognifide.aemrules.matcher.MethodMatcher;
import com.cognifide.aemrules.matcher.MethodNamePredicate;
import com.cognifide.aemrules.matcher.OwnerTypePredicate;
import com.cognifide.aemrules.matcher.ParameterTypePredicate;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
    key = AdministrativeAccessUsageCheck.RULE_KEY,
    name = AdministrativeAccessUsageCheck.RULE_MESSAGE,
    priority = Priority.MAJOR,
    tags = Tags.AEM
)
@AemVersion(
    from = "6.0"
)
@Metadata(
    technicalDebt = "30min"
)
public class AdministrativeAccessUsageCheck extends IssuableSubscriptionVisitor {

    public static final String RULE_KEY = "AEM-11";

    public static final String RULE_MESSAGE = "Do not use deprecated administrative access methods";

    private static final Map<String, String> SUBSTITUTES = ImmutableMap.<String, String>builder()
        .put("loginAdministrative", "loginService")
        .put("getAdministrativeResourceResolver", "getServiceResourceResolver")
        .build();

    private static final List<MethodMatcher> matchers = ImmutableList.of(
        MethodMatcher.create(
            MethodNamePredicate.is("loginAdministrative"),
            OwnerTypePredicate.is("org.apache.sling.jcr.api.SlingRepository"),
            ParameterTypePredicate.is("java.lang.String")),
        MethodMatcher.create(
            MethodNamePredicate.is("getAdministrativeResourceResolver"),
            OwnerTypePredicate.is("org.apache.sling.api.resource.ResourceResolverFactory"),
            ParameterTypePredicate.is("java.util.Map"))
    );

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (this.hasSemantic()) {
            matchers.forEach(invocationMatcher -> this.checkInvocation(tree, invocationMatcher));
        }
    }

    private void checkInvocation(Tree tree, MethodMatcher invocationMatcher) {
        if (tree.is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
            if (invocationMatcher.matches(methodInvocationTree)) {
                this.onMethodInvocationFound(methodInvocationTree);
            }
        }
    }

    private void onMethodInvocationFound(MethodInvocationTree mit) {
        String method = ((MemberSelectExpressionTree) mit.methodSelect()).identifier().name();
        context.reportIssue(this, mit, String.format("Method '%s' is deprecated. Use '%s' instead.", method, SUBSTITUTES.get(method)));
    }
}
