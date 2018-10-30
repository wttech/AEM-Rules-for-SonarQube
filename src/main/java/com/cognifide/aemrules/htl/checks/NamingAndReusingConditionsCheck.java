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

    static final String RULE_KEY = "HTL-5";

    static final String RULE_MESSAGE = "Consider caching data-sly-test conditions";

    private Set<String> conditions = new HashSet<>();

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        if (isANewTest(node) && isRepeatedCondition(expressions)) {
            createViolation(node.getStartLinePosition(), RULE_MESSAGE);
        }
        conditions.addAll(expressions.stream().map(Expression::getRawText).collect(Collectors.toSet()));
        super.startHtlElement(expressions, node);
    }

    private boolean isANewTest(TagNode node) {
        return node.getAttributes().stream().anyMatch(attribute -> attribute.getName().equals("data-sly-test"));
    }

    private boolean isRepeatedCondition(List<Expression> expressions) {
        return expressions.stream().map(Expression::getRawText).anyMatch(expression -> conditions.contains(expression));
    }
}
