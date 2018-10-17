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

import com.cognifide.aemrules.util.TypeUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.TypeTree;

class ClassVisitor extends BaseTreeVisitor {

    private static final String SLICE_RESOURCE_ANNOTATION = "com.cognifide.slice.mapper.annotation.SliceResource";

    private static final String INITIALIZABLE_MODEL_INTERFACE = "com.cognifide.slice.api.model.InitializableModel";

    private final JavaFileScannerContext context;

    private final JavaFileScanner javaFileScanner;

    private boolean sliceAnnotationPresent;

    private boolean implementedInitializableModel;

    private boolean ignoreAnonymousClass;

    ClassVisitor(JavaFileScannerContext context, JavaFileScanner javaFileScanner) {
        this.context = context;
        this.javaFileScanner = javaFileScanner;
        this.sliceAnnotationPresent = false;
        this.implementedInitializableModel = false;
        this.ignoreAnonymousClass = false;
    }

    @Override
    public void visitClass(ClassTree classTree) {
        for (AnnotationTree annotation : classTree.modifiers().annotations()) {
            sliceAnnotationPresent |= TypeUtils.isOfType(annotation.annotationType(), SLICE_RESOURCE_ANNOTATION);
        }
        for (TypeTree typeTree : classTree.superInterfaces()) {
            implementedInitializableModel |= TypeUtils.isOfType(typeTree, INITIALIZABLE_MODEL_INTERFACE);
        }
        super.visitClass(classTree);
    }

    @Override
    public void visitNewClass(NewClassTree tree) {
        if (!ignoreAnonymousClass) {
            ClassTree classBody = tree.classBody();
            if (null != classBody) {
                classBody.accept(new ClassVisitor(context, javaFileScanner));
            }
        }
    }

    @Override
    public void visitMethod(MethodTree method) {
        if (sliceAnnotationPresent
            && !isConstructorOrAfterCreatedMethod(method)
            && !method.symbol().isPrivate()) {
            method.accept(new MethodInvocationVisitor(javaFileScanner, context));
        } else {
            ignoreAnonymousClass = true;
            super.visitMethod(method);
        }
        ignoreAnonymousClass = false;
    }

    private boolean isConstructorOrAfterCreatedMethod(MethodTree tree) {
        return tree.is(MethodTree.Kind.CONSTRUCTOR)
            || (implementedInitializableModel && "afterCreated".equals(tree.symbol().name()));
    }
}
