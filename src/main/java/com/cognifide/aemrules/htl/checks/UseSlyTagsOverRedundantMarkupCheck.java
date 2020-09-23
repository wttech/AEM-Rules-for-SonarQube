/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Cognifide Limited
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
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Rule(
        key = UseSlyTagsOverRedundantMarkupCheck.RULE_KEY,
        name = UseSlyTagsOverRedundantMarkupCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class UseSlyTagsOverRedundantMarkupCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-10";

    public static final String RULE_MESSAGE = "Use sly tags over redundant markup";

    private static final String RULE_VIOLATION = "Try to reduce redundant markup with sly tags";

    private static final String SLY_TAG = "sly";

    private static final List<String> SLY_ATTRIBUTES = Arrays.asList(
            "data-sly-use",
            "data-sly-include",
            "data-sly-resource",
            "data-sly-call"
    );

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        if (containsRedundantHtmlContainer(node) || isWrappedInRedundantMarkup(node, expressions)) {
            createViolation(node.getStartLinePosition(), RULE_VIOLATION);
        }
    }

    private boolean isWrappedInRedundantMarkup(TagNode node, List<Expression> expressions) {
        return !StringUtils.equalsIgnoreCase(SLY_TAG, node.getNodeName()) &&
                containsSlyCallAttributeWithExpression(node, expressions) &&
                node.getChildren().isEmpty();
    }

    private boolean containsRedundantHtmlContainer(TagNode node){
        return StringUtils.equalsIgnoreCase("div", node.getNodeName()) &&
                node.getAttribute("data-sly-test") != null &&
                node.getAttribute("class") == null;
    }

    private boolean isUsingCallAttributes(TagNode node){
        return node.getAttributes().stream()
                .map(Attribute::getName)
                .map(s -> StringUtils.substringBefore(s, "."))
                .anyMatch(SLY_ATTRIBUTES::contains);
    }

    private boolean containsSlyCallAttributeWithExpression(TagNode node, List<Expression> expressions) {
        if (expressions.isEmpty()) {
            return isUsingCallAttributes(node);
        }

        List<String> slyAttributesExpression = node.getAttributes().stream()
                .filter(attribute -> SLY_ATTRIBUTES.contains(attribute.getName()))
                .map(Attribute::getValue)
                .collect(Collectors.toList());

        return expressions.stream()
                .map(Expression::getRawText)
                .anyMatch(slyAttributesExpression::contains);
    }

}

