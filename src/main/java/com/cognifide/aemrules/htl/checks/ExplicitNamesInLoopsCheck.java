package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import java.util.List;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;

@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "10min"
)
@Rule(
    key = ExplicitNamesInLoopsCheck.RULE_KEY,
    name = ExplicitNamesInLoopsCheck.RULE_NAME,
    priority = Priority.MINOR,
    tags = Tags.AEM
)

public class ExplicitNamesInLoopsCheck extends AbstractHtlCheck {

    static final String RULE_KEY = "HTL-3";

    static final String RULE_NAME = "Explicit names in loops";

    static final String RULE_MESSAGE = "Use explicit names in loops";

    @Override
    public void htlExpression(Expression expression, Node node) {
        if (node instanceof TagNode) {
            if (((TagNode) node).getAttributes().iterator().next().getName().equals("data-sly-list")) {
                createViolation(node.getStartLinePosition(), RULE_MESSAGE);
            }
        }
    }

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        super.startHtlElement(expressions, node);
    }


}
