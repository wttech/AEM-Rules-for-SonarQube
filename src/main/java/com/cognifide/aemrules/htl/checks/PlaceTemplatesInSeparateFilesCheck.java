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
package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.compiler.expression.nodes.Identifier;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

@Rule(
    key = PlaceTemplatesInSeparateFilesCheck.RULE_KEY,
    name = PlaceTemplatesInSeparateFilesCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    from = "6.0"
)
@Metadata(
    technicalDebt = "15min"
)
public class PlaceTemplatesInSeparateFilesCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-2";

    public static final String RULE_MESSAGE = "HTL Templates should be placed in separate files";

    private Map<Integer, String> templatesDefinition;

    private Map<Integer, String> templatesCalls;

    public PlaceTemplatesInSeparateFilesCheck() {
        templatesDefinition = new HashMap<>();
        templatesCalls = new HashMap<>();
    }

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        node.getAttributes().stream()
            .filter(this::isTemplateAttribute)
            .forEach(attribute -> templatesDefinition.put(attribute.getLine(), getTemplateName(attribute)));

        node.getAttributes().stream()
            .filter(this::isCallAttribute)
            .forEach(attribute -> templatesCalls.put(attribute.getLine(), getTemplateUsage(attribute)));
    }

    @Override
    public void endDocument() {
        checkTemplateUsage();
    }

    private boolean isTemplateAttribute(Attribute attribute) {
        String attributeName = attribute.getName();
        return StringUtils.startsWith(attributeName, "data-sly-template");
    }

    private String getTemplateName(Attribute attribute) {
        return StringUtils.removeStart(attribute.getName(), "data-sly-template.");
    }

    private boolean isCallAttribute(Attribute attribute) {
        String attributeName = attribute.getName();
        return StringUtils.startsWith(attributeName, "data-sly-call");
    }

    private String getTemplateUsage(Attribute attribute) {
        return getExpressions(attribute.getValue()).stream()
            .map(Expression::getRoot)
            .filter(Identifier.class::isInstance)
            .map(Identifier.class::cast)
            .map(Identifier::getName)
            .findFirst()
            .orElse(StringUtils.EMPTY);
    }

    private void checkTemplateUsage() {
        for (Map.Entry<Integer, String> callEntry : templatesCalls.entrySet()) {
            String templateUsage = callEntry.getValue();
            for (Map.Entry<Integer, String> templateEntry : templatesDefinition.entrySet()) {
                String templateDefinitionName = templateEntry.getValue();
                if (StringUtils.equals(templateUsage, templateDefinitionName)) {
                    createViolation(callEntry.getKey(), RULE_MESSAGE);
                }
            }
        }
    }
}
