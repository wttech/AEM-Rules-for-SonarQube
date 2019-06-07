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