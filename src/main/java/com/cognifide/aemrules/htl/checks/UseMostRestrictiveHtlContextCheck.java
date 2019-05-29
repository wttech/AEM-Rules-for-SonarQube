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
import org.sonar.plugins.html.node.TagNode;

import java.util.regex.Pattern;

import static org.apache.sling.scripting.sightly.impl.compiler.Syntax.CONTEXT_OPTION;

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

    private static final String DATA_ATTRIBUTE = "data";

    private static final Pattern LITERAL_EXPRESION_PATTERN = Pattern.compile("\\$\\{.*}");

    @Override
    public void startElement(TagNode node) {
        node.getAttributes().stream()
                .filter(this::isContextMandatory)
                .forEach(attribute -> createViolation(attribute.getLine(), RULE_MESSAGE));
    }

    private boolean isContextMandatory(Attribute attribute) {
        String value = attribute.getValue();
        return attribute.getName().startsWith(DATA_ATTRIBUTE) &&
                LITERAL_EXPRESION_PATTERN.matcher(value).find() &&
                !value.contains(CONTEXT_OPTION);
    }
}
