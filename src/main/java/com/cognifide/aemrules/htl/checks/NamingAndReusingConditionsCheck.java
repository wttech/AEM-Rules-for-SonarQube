/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Wunderman Thompson Technology
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
package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.api.ParsingErrorRule;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

@Rule(
    key = NamingAndReusingConditionsCheck.RULE_KEY,
    name = NamingAndReusingConditionsCheck.RULE_MESSAGE,
    priority = Priority.INFO,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "10min"
)
@ParsingErrorRule
public class NamingAndReusingConditionsCheck extends AbstractHtlCheck {

    static final String RULE_KEY = "HTL-4";

    static final String RULE_MESSAGE = "Consider caching data-sly-test conditions";

    private static final String SLY_TEST = "data-sly-test";

    private static final int SLY_TEST_LENGTH = 14;

    private Set<String> unnamedConditions = new HashSet<>();

    private Set<String> namedConditions = new HashSet<>();

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        if (!isConditionReusedCorrectly(expressions, node)) {
            createViolation(node.getStartLinePosition(), RULE_MESSAGE);
        }
        updateConditionSets(expressions, node);
    }

    @Override
    public void endDocument() {
        unnamedConditions.clear();
        namedConditions.clear();
    }

    private boolean isConditionReusedCorrectly(List<Expression> expressions, TagNode node) {
        String condition = clearExpressions(expressions).stream()
            .findFirst()
            .orElse("");

        return !(isUnnamedConditionReused(condition) && isNewUnnamedConditionDeclared(node));
    }

    private boolean isNewUnnamedConditionDeclared(TagNode node) {
        return node.getAttributes().stream()
            .map(Attribute::getName)
            .anyMatch(SLY_TEST::equals);
    }

    private boolean isUnnamedConditionReused(String condition) {
        return unnamedConditions.stream()
            .anyMatch(condition::equals);
    }

    private void updateConditionSets(List<Expression> expressions, TagNode node) {
        String condition = node.getAttributes().stream()
            .map(Attribute::getName)
            .filter(text -> text.contains(SLY_TEST))
            .findFirst()
            .orElse("");
        if (!SLY_TEST.equals(condition) && SLY_TEST_LENGTH < condition.length()) {
            condition = condition.substring(SLY_TEST_LENGTH);
            namedConditions.add(condition);
        } else {
            unnamedConditions.addAll(clearExpressions(expressions).stream()
                .filter(text -> !namedConditions.contains(text))
                .collect(Collectors.toSet()));
        }
    }

    private Set<String> clearExpressions(List<Expression> expressions){
        return expressions.stream()
            .map(Expression::getRawText)
            .map(text -> text.replaceAll("[${}]", ""))
            .collect(Collectors.toSet());
    }
}