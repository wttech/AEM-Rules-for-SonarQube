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

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;

import java.util.regex.Pattern;

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

    private static final String UNSAFE_DISPLAY_CONTEXT = "unsafe";

    private static final Pattern LITERAL_EXPRESION_PATTERN = Pattern.compile("\\$\\{.*}");

    @Override
    public void characters(TextNode textNode) {
        verifyDisplayContext(textNode);
    }

    @Override
    public void expression(ExpressionNode node) {
        verifyDisplayContext(node);
    }

    @Override
    public void startElement(TagNode node) {
        node.getAttributes().stream()
                .map(Attribute::getValue)
                .filter(this::isUnsafeContextDefined)
                .forEach(s -> createViolation(node.getStartLinePosition(), RULE_MESSAGE));
    }

    private void verifyDisplayContext(Node node) {
        String text = node.getCode();
        if (isUnsafeContextDefined(text)) {
            createViolation(node.getStartLinePosition(), RULE_MESSAGE);
        }
    }

    private boolean isUnsafeContextDefined(String text) {
        return LITERAL_EXPRESION_PATTERN.matcher(text).find() &&
                text.contains(UNSAFE_DISPLAY_CONTEXT);
    }
}
