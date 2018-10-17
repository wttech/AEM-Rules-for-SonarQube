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
package com.cognifide.aemrules.checks.visitors;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.TreeVisitor;
import org.sonar.plugins.java.api.tree.TryStatementTree;

/**
 * Visits only finally blocks. Used only in methods.
 */
public class FinallyBlockVisitor extends BaseTreeVisitor {

    private final TreeVisitor visitor;

    public FinallyBlockVisitor(TreeVisitor visitor) {
        this.visitor = visitor;
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {
        if (tree.finallyBlock() != null) {
            tree.finallyBlock().accept(visitor);
        }
        super.visitTryStatement(tree);
    }
}
