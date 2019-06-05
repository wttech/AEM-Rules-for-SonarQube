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
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

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

    private static final ImmutableList SLY_ATTRIBUTES = ImmutableList.of("data-sly-use",
            "data-sly-test",
            "data-sly-resource",
            "data-sly-call");

    @Override
    public void startElement(TagNode node) {
        if (!StringUtils.equalsAnyIgnoreCase(SLY_TAG, node.getNodeName()) && isReferenceBlockStatement(node)) {
            createViolation(node.getStartLinePosition(), RULE_VIOLATION);
        }
    }

    private boolean isReferenceBlockStatement(TagNode node) {
        return node.getAttributes().stream()
                .map(Attribute::getName)
                .map(s -> StringUtils.substringBefore(s, "."))
                .anyMatch(SLY_ATTRIBUTES::contains);
    }

}

