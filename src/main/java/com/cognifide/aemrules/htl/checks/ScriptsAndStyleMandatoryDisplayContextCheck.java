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
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;

@Rule(
        key = ScriptsAndStyleMandatoryDisplayContextCheck.RULE_KEY,
        name = ScriptsAndStyleMandatoryDisplayContextCheck.RULE_MESSAGE,
        priority = Priority.MAJOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class ScriptsAndStyleMandatoryDisplayContextCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-7";

    public static final String RULE_MESSAGE = "For style and script tags display context definition is mandatory";

    private static final String VIOLATION_MESSAGE = "Please define display context for literal expression";

    private static final String SCRIPT_TAG_NAME = "script";

    private static final String STYLE_TAG_NAME = "style";

    private boolean isEmbeddedInstructionNode = false;

    @Override
    public void startElement(TagNode node) {
        String nodeName = node.getNodeName();
        if (nodeName.equalsIgnoreCase(SCRIPT_TAG_NAME) || nodeName.equalsIgnoreCase(STYLE_TAG_NAME)) {
            isEmbeddedInstructionNode = true;
        }
    }

    @Override
    public void htlExpression(Expression expression, Node node) {
        if (isEmbeddedInstructionNode && !expression.containsOption(Syntax.CONTEXT_OPTION)) {
            createViolation(node.getStartLinePosition(), VIOLATION_MESSAGE);
        }
    }

    @Override
    public void endElement(TagNode node) {
        isEmbeddedInstructionNode = false;
    }
}
