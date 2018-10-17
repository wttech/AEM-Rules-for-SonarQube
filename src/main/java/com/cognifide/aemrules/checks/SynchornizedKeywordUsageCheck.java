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
package com.cognifide.aemrules.checks;

import static com.cognifide.aemrules.checks.SynchornizedKeywordUsageCheck.DESCRIPTION;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.ModifierKeywordTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
    key = SynchornizedKeywordUsageCheck.RULE_KEY,
    name = SynchornizedKeywordUsageCheck.MESSAGE,
    description = DESCRIPTION,
    priority = Priority.INFO,
    tags = {Tags.MULTI_THREADING, Tags.PERFORMANCE}
)
@AemVersion(
    all = true
)
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
        Kind i = tree.kind();
        if (i == Kind.METHOD) {
            SynchronizedMethodVisitor visitor = new SynchronizedMethodVisitor(this);
            tree.accept(visitor);
        } else if (i == Kind.SYNCHRONIZED_STATEMENT) {
            reportIssue(tree, MESSAGE);
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
