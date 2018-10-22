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
package com.cognifide.aemrules.checks.slice.session;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import javax.annotation.Nonnull;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

/**
 * @author Krzysztof Watral
 */
@Rule(
    key = ModelsShouldNotUseSessionCheck.RULE_KEY,
    name = ModelsShouldNotUseSessionCheck.RULE_MESSAGE,
    priority = Priority.BLOCKER,
    tags = {Tags.AEM, Tags.SLICE}
)
@AemVersion(
    all = true
)
public class ModelsShouldNotUseSessionCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_KEY = "AEM-9";

    public static final String RULE_MESSAGE
        = "Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(@Nonnull JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        tree.accept(new ClassVisitor(context, this));
    }

}
