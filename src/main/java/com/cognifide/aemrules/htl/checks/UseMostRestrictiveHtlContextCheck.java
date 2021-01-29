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

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

@Rule(
        key = UseMostRestrictiveHtlContextCheck.RULE_KEY,
        name = UseMostRestrictiveHtlContextCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class UseMostRestrictiveHtlContextCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-12";

    public static final String RULE_MESSAGE = "Use the most restrictive HTL context possible.";

    private static final String DATA_ATTRIBUTE_PREFIX = "data";

    @Override
    public void startElement(TagNode node) {
        node.getAttributes().stream()
                .filter(this::isHtmlDataAttribute)
                .filter(this::isNotContextDefined)
                .forEach(attribute -> createViolation(attribute.getLine(), RULE_MESSAGE));
    }

    private boolean isHtmlDataAttribute(Attribute attribute) {
        String name = attribute.getName();
        return StringUtils.startsWith(name, DATA_ATTRIBUTE_PREFIX) &&
                !StringUtils.startsWith(name, Syntax.PLUGIN_ATTRIBUTE_PREFIX);
    }

    private boolean isNotContextDefined(Attribute attribute) {
        return  getExpressions(attribute.getValue()).stream()
                .anyMatch(expression -> !expression.containsOption(Syntax.CONTEXT_OPTION));
    }
}
