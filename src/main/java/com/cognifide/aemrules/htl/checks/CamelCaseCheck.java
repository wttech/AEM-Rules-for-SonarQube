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
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

@Rule(
    key = CamelCaseCheck.RULE_KEY,
    name = CamelCaseCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    from = "6.0"
)
@Metadata(
    technicalDebt = "5min"
)
public class CamelCaseCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-15";

    static final String RULE_MESSAGE = "Use Camel Case in identifiers";

    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([a-z]+([A-Z][a-z]+)+)");

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        for (Attribute attribute : node.getAttributes()) {
            String attributeName = attribute.getName();
            String variableName = StringUtils.substringAfter(attributeName, ".");
            if (StringUtils.isNotEmpty(variableName)) {
                checkVariableName(variableName, attribute);
            }
        }
    }

    private void checkVariableName(@Nonnull String name, Attribute node) {
        boolean isCamelCase = CAMEL_CASE_PATTERN.matcher(name).matches();
        boolean isLowercase = StringUtils.isAllLowerCase(name);
        if (!(isCamelCase || isLowercase)) {
            createViolation(node.getLine(), RULE_MESSAGE);
        }
    }
}
