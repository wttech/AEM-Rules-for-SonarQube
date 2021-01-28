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
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Rule(
        key = AvoidExtraSlyTagsCheck.RULE_KEY,
        name = AvoidExtraSlyTagsCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class AvoidExtraSlyTagsCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-11";

    public static final String RULE_MESSAGE = "Use existing HTML elements instead of adding extra sly tags";

    private static final String SLY_TAG = "sly";

    private static final List<String> SLY_ATTRIBUTES = Arrays.asList("data-sly-use",
            "data-sly-test",
            "data-sly-call");

    @Override
    public void startElement(TagNode node) {
        Optional.ofNullable(node.getParent())
                .ifPresent(parentNode ->
                {
                    if (!StringUtils.equalsAnyIgnoreCase(SLY_TAG,node.getNodeName()) &&
                            StringUtils.equalsAnyIgnoreCase(SLY_TAG, parentNode.getNodeName()) &&
                            parentNode.getChildren().size() <= 1){
                        parentNode.getAttributes().stream()
                                .map(Attribute::getName)
                                .filter(SLY_ATTRIBUTES::contains)
                                .findFirst()
                                .ifPresent(s -> createViolation(parentNode.getStartLinePosition(), RULE_MESSAGE));
                    }
                });
    }
}

