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

@Rule(
    key = AvoidInlineCodeCheck.RULE_KEY,
    name = AvoidInlineCodeCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    from = "6.0"
)
@Metadata(
    technicalDebt = "15min"
)
public class AvoidInlineCodeCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-8";

    public static final String RULE_MESSAGE = "Avoid inline code";

    @Override
    public void startElement(TagNode tagNode) {
        if (isInlineScript(tagNode) || isInlineStyle(tagNode)) {
            createViolation(tagNode.getStartLinePosition(), RULE_MESSAGE);
        }
    }

    private boolean isInlineScript(TagNode tagNode) {
        boolean isScriptTag = StringUtils.equals("script", tagNode.getNodeName());
        boolean hasNoSrcAttribute = tagNode.getAttribute("src") == null;
        return isScriptTag && hasNoSrcAttribute;
    }

    private boolean isInlineStyle(TagNode tagNode) {
        return StringUtils.equals("style", tagNode.getNodeName());
    }
}
