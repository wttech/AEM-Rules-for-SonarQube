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

import com.cognifide.aemrules.htl.api.HtlCheck;
import com.cognifide.aemrules.htl.visitors.DefaultHtlVisitor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.ExpressionParser;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.Fragment;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.html.checks.HtmlIssue;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class AbstractHtlCheck implements DefaultHtlVisitor, HtlCheck {

    private HtmlSourceCode htmlSourceCode;

    private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

    private RuleKey ruleKey;

    protected static List<Expression> getExpressions(String code) {
        Iterable<Fragment> fragments = EXPRESSION_PARSER.parseInterpolation(code).getFragments();
        return StreamSupport.stream(fragments.spliterator(), false)
            .filter(Fragment::isExpression)
            .map(Fragment::getExpression)
            .collect(Collectors.toList());
    }

    @Override
    public final void setRuleKey(RuleKey ruleKey) {
        this.ruleKey = ruleKey;
    }

    @Override
    public final void createViolation(Integer line, String message) {
        getHtmlSourceCode().addIssue(
            new HtmlIssue(ruleKey, line, message)
        );
    }

    @Override
    public final void createViolation(Integer line, String message, Double cost) {
        getHtmlSourceCode().addIssue(
            new HtmlIssue(ruleKey, line, message, cost)
        );
    }

    @Override
    public HtmlSourceCode getHtmlSourceCode() {
        return htmlSourceCode;
    }

    @Override
    public void setSourceCode(HtmlSourceCode sourceCode) {
        this.htmlSourceCode = sourceCode;
    }


}
