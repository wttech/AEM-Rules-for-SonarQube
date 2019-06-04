package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
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

  @Override
  public void comment(CommentNode node) {
    if (!isHTLComment(node)) {
      createViolation(node.getStartLinePosition(), RULE_MESSAGE);
    }
  }

  private boolean isHTLComment(CommentNode node) {
    String comment = node.getCode();
    return comment.startsWith("<!--/*") && comment.endsWith("*/-->");
  }
}