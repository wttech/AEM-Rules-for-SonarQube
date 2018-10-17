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

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.util.ConstantsChecker;
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
    key = ConstantsCheck.RULE_KEY,
    name = ConstantsCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
public class ConstantsCheck extends IssuableSubscriptionVisitor {

    public static final String RULE_MESSAGE = "Use predefined constant instead of hardcoded value.";

    protected static final String RULE_KEY = "AEM-2";

    @Override
    public List<Kind> nodesToVisit() {
        return Lists.newArrayList(Kind.STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree stringLiteral) {
        String literalValue = removeQuotes(((LiteralTree) stringLiteral).value());
        if (ConstantsChecker.isConstant(literalValue)) {
            String message = ConstantsChecker.getMessageForConstant(literalValue);
            reportIssue(stringLiteral, String.format("Use constant %s instead of hardcoded value.", message));
        }
        super.visitNode(stringLiteral);
    }

    private String removeQuotes(String value) {
        return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
    }


}
