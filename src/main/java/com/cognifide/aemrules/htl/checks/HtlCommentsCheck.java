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
import java.util.regex.Pattern;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.CommentNode;

@Rule(
    key = HtlCommentsCheck.RULE_KEY,
    name = HtlCommentsCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "5min"
)
public class HtlCommentsCheck extends AbstractHtlCheck {

  static final String RULE_KEY = "HTL-5";

  static final String RULE_MESSAGE = "Always use HTL style of comments";

  private static final Pattern CONDITIONAL_COMMENT_PATTERN = Pattern
      .compile("<!--\\[.*]-->");

  private static final Pattern ESI_COMMENT_PATTERN = Pattern
      .compile("<!--esi.*-->");

  @Override
  public void comment(CommentNode node) {
    String code = node.getCode();
    if (!Syntax.isSightlyComment(code) && !isException(code)) {
      createViolation(node.getStartLinePosition(), RULE_MESSAGE);
    }
  }

  private boolean isException(String code) {
    return isConditionalComment(code) || isESIComment(code);
  }

  private boolean isConditionalComment(String code) {
    return CONDITIONAL_COMMENT_PATTERN.matcher(removeWhitespace(code)).matches();
  }

  private boolean isESIComment(String code) {
    return ESI_COMMENT_PATTERN.matcher(removeWhitespace(code)).matches();
  }

  private String removeWhitespace(String code) {
    return code.replaceAll("\\s+", "");
  }
}