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

import com.cognifide.aemrules.htl.visitors.HtlStringOptionVisitor;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.compiler.expression.MarkupContext;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Node;

@Rule(
        key = AvoidUsingUnsafeDisplayContextCheck.RULE_KEY,
        name = AvoidUsingUnsafeDisplayContextCheck.RULE_MESSAGE,
        priority = Priority.CRITICAL,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class AvoidUsingUnsafeDisplayContextCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-13";

    public static final String RULE_MESSAGE = "Avoid using 'unsafe' display context, this disables XSS protection completely.";

    @Override
    public void htlExpression(Expression expression, Node node) {
        if (expression.containsOption(Syntax.CONTEXT_OPTION)) {
            String context = expression.getOptions().get(Syntax.CONTEXT_OPTION).accept(new HtlStringOptionVisitor());
            if (StringUtils.equalsAnyIgnoreCase(context, MarkupContext.UNSAFE.getName())) {
                createViolation(node.getStartLinePosition(), RULE_MESSAGE);
            }
        }

    }
}
