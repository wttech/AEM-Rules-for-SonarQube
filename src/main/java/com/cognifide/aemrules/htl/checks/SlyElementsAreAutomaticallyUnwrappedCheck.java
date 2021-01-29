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
        key = SlyElementsAreAutomaticallyUnwrappedCheck.RULE_KEY,
        name = SlyElementsAreAutomaticallyUnwrappedCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class SlyElementsAreAutomaticallyUnwrappedCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-16";

    public static final String RULE_MESSAGE = "HTL <sly> elements are automatically unwrapped";

    private static final String VIOLATION_MESSAGE = "Please remove redundant data-sly-unwrap attribute";

    private static final String SLY_TAG = "sly";

    private static final String UNWRAP_ATTRIBUTE_NAME = Syntax.PLUGIN_ATTRIBUTE_PREFIX + "unwrap";

    @Override
    public void startElement(TagNode node) {
        if (SLY_TAG.equalsIgnoreCase(node.getNodeName())) {
            node.getAttributes().stream()
                    .filter(this::containsRedundantUnwrapAttr)
                    .findFirst()
                    .ifPresent(attribute -> createViolation(node.getStartLinePosition(), VIOLATION_MESSAGE));
        }
    }

    private boolean containsRedundantUnwrapAttr(Attribute attribute) {
        return UNWRAP_ATTRIBUTE_NAME.equalsIgnoreCase(attribute.getName()) &&
                StringUtils.isBlank(attribute.getValue()) &&
                getExpressions(attribute.getValue()).isEmpty();
    }
}
