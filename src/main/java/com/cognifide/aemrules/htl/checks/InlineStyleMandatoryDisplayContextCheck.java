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
import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.TagNode;

import java.util.regex.Pattern;

@Rule(
        key = InlineStyleMandatoryDisplayContextCheck.RULE_KEY,
        name = InlineStyleMandatoryDisplayContextCheck.RULE_MESSAGE,
        priority = Priority.MAJOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class InlineStyleMandatoryDisplayContextCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-9";

    public static final String RULE_MESSAGE = "For inline styles display context is mandatory";

    private static final String VIOLATION_MESSAGE = "Please define display context";

    private static final Pattern LITERAL_EXPRESION_PATTERN = Pattern.compile("\\$\\{.*}");

    private static final String DISPLAY_CONTEXT_ATTRIBUTE = "context";

    @Override
    public void startElement(TagNode node) {
        node.getAttributes()
                .stream()
                .filter(attribute -> StringUtils.equals(attribute.getName(), "style"))
                .filter(attribute -> LITERAL_EXPRESION_PATTERN.matcher(attribute.getValue()).find() && !attribute.getValue().contains(DISPLAY_CONTEXT_ATTRIBUTE))
                .forEach(attribute -> createViolation(node.getStartLinePosition(), VIOLATION_MESSAGE));
    }

}
