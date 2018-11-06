package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Node;
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
    public void htlExpression(Expression expression, Node node) {
        if (node instanceof TagNode && (isSlyList((TagNode)node) || isSlyRepeat((TagNode)node))) {
            createViolation(node.getStartLinePosition(), RULE_MESSAGE);
        }
    }

    private boolean isSlyList(TagNode node) {
        return "data-sly-list".equals(node.getAttributes().iterator().next().getName());
    }

    private boolean isSlyRepeat(TagNode node) {
        return "data-sly-repeat".equals(node.getAttributes().iterator().next().getName());
    }
}
