/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
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
package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.util.ConstantsChecker;
import com.cognifide.aemrules.version.AemVersion;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(
    key = AnnotationsConstantsCheck.RULE_KEY,
    name = AnnotationsConstantsCheck.RULE_MESSAGE,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    all = true
)
public class AnnotationsConstantsCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-1";

    public static final String RULE_MESSAGE = "Use predefined constant in annotation instead of hardcoded value.";

    private JavaFileScannerContext context;

    private boolean inAnnotation;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitAnnotation(AnnotationTree annotationTree) {
        inAnnotation = true;
        super.visitAnnotation(annotationTree);
        inAnnotation = false;
    }

    @Override
    public void visitLiteral(LiteralTree tree) {
        if (inAnnotation && tree.is(Kind.STRING_LITERAL)) {
            String literalValue = removeQuotes(tree.value());
            if (ConstantsChecker.isAnnotationConstant(literalValue)) {
                context.reportIssue(this, tree,
                    String.format("Use constant %s instead of hardcoded value.", ConstantsChecker.getAnnotationMessageForConstant(literalValue)));
            }
        }
        super.visitLiteral(tree);
    }

    private String removeQuotes(String value) {
        return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
    }
}
