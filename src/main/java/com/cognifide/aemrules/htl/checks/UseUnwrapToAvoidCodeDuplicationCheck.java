package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.apache.sling.scripting.sightly.impl.plugin.PluginCallInfo;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.TagNode;

@AemVersion(
    from = "6.0"
)
@Metadata(
    technicalDebt = "5min"
)
@Rule(
    key = UseUnwrapToAvoidCodeDuplicationCheck.RULE_KEY,
    name = UseUnwrapToAvoidCodeDuplicationCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
public class UseUnwrapToAvoidCodeDuplicationCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-5";
    public static final String RULE_MESSAGE = "HTL-5";

    @Override
    public void startHtlElement(List<Expression> expressions, TagNode node) {
        Expression testExpression = getTestExpression(node);
        testExpression.getRoot();
    }

    private Expression getTestExpression(TagNode node) {
        return node.getAttributes().stream()
            .filter(new IsTestPredicate())
            .map(Attribute::getValue)
            .map(AbstractHtlCheck::getExpressions)
            .flatMap(Collection::stream)
            .findFirst()
            .orElse(null);
    }

    private class IsTestPredicate implements Predicate<Attribute> {

        @Override
        public boolean test(Attribute attribute) {
            return Optional.ofNullable(attribute)
                .map(Attribute::getName)
                .map(Syntax::parsePluginAttribute)
                .filter(Objects::nonNull)
                .map(PluginCallInfo::getName)
                .map(this::isTestPlugin)
                .orElse(false);
        }


        private boolean isTestPlugin(String pluginName) {
            return StringUtils.equals(pluginName, "test");
        }
    }
}
