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
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.TagNode;

@Rule(
    key = ExplicitNamesInLoopsCheck.RULE_KEY,
    name = ExplicitNamesInLoopsCheck.RULE_NAME,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "10min"
)
public class ExplicitNamesInLoopsCheck extends AbstractHtlCheck {

  static final String RULE_KEY = "HTL-3";

  static final String RULE_NAME = "Explicit names in loops";

  static final String RULE_MESSAGE = "Use explicit names in loops";

  @Override
  public void startElement(TagNode node) {
    if (isSlyList(node) || isSlyRepeat(node)) {
      createViolation(node.getStartLinePosition(), RULE_MESSAGE);
    }
  }

  private boolean isSlyList(TagNode node) {
    return node.getAttributes().stream()
        .anyMatch(attribute -> "data-sly-list".equals(attribute.getName()));
  }

  private boolean isSlyRepeat(TagNode node) {
    return node.getAttributes().stream()
        .anyMatch(attribute -> "data-sly-repeat".equals(attribute.getName()));
  }
}