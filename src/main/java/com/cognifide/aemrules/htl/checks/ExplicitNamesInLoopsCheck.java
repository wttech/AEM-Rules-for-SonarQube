package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.visitors.DefaultHtlVisitor;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.version.AemVersion;
import java.util.List;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.TagNode;

@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "10min"
)
@Rule(
)

public class ExplicitNamesInLoopsCheck extends DefaultHtlVisitor {

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        super.startHtlElement(expressions, node);
    }
}
