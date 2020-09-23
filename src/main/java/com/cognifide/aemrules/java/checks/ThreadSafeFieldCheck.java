/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Cognifide Limited
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
package com.cognifide.aemrules.java.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
    key = ThreadSafeFieldCheck.RULE_KEY,
    name = ThreadSafeFieldCheck.RULE_NAME,
    priority = Priority.CRITICAL,
    tags = {Tags.BUG, Tags.AEM}
)
@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "1h"
)
public class ThreadSafeFieldCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_NAME = "Non-thread safe object used as a field of Servlet / Filter etc.";

    public static final String RULE_KEY = "AEM-3";

    public static final String RULE_MESSAGE = "Usage of %s as a field is not thread safe.";

    private static final Set<String> VULNERABLE_CLASSES = Collections.emptySet(); // empty for now

    private static final Set<String> VULNERABLE_INTERFACES = Stream.of(
        "javax.servlet.Servlet",
        "javax.servlet.Filter",
        "org.osgi.service.event.EventHandler"
    ).collect(Collectors.toSet());

    private static final Set<String> VULNERABLE_ANNOTATIONS = Stream.of(
        "org.apache.felix.scr.annotations.Component",
        "org.osgi.service.component.annotations.Component",
        "org.apache.felix.scr.annotations.sling.SlingServlet", // this is possibly duplicative, but that shouldn't be a problem.
        "org.apache.felix.scr.annotations.sling.SlingFilter" // this is possibly duplicative, but that shouldn't be a problem.
    ).collect(Collectors.toSet());

    private static final Set<String> NON_THREAD_SAFE_TYPES = Stream.of(
        "org.apache.sling.api.resource.ResourceResolver",
        "javax.jcr.Session",
        "com.day.cq.wcm.api.PageManager",
        "com.day.cq.wcm.api.components.ComponentManager",
        "com.day.cq.wcm.api.designer.Designer",
        "com.day.cq.dam.api.AssetManager",
        "com.day.cq.tagging.TagManager",
        "com.day.cq.security.UserManager",
        "org.apache.jackrabbit.api.security.user.Authorizable",
        "org.apache.jackrabbit.api.security.user.User",
        "org.apache.jackrabbit.api.security.user.UserManager"
    ).collect(Collectors.toSet());

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext javaFileScannerContext) {
        context = javaFileScannerContext;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree classTree) {
        boolean extendsClass = extendsVulnerableClass(classTree);
        boolean implementsInterface = implementsVulnerableInterface(classTree);
        boolean hasAnnotation = hasAnnotation(classTree);
        boolean vulnerable = extendsClass || implementsInterface || hasAnnotation;
        if (vulnerable) {
            checkMembers(classTree);
        }
        super.visitClass(classTree);
    }

    private void checkMembers(ClassTree classTree) {
        for (Tree member : classTree.members()) {
            checkMember(member);
        }
    }

    private void checkMember(Tree member) {
        boolean isVariableField = member.is(Kind.VARIABLE);
        if (isVariableField) {
            VariableTree variableField = (VariableTree) member;
            String name = variableField.type().symbolType().fullyQualifiedName();
            if (NON_THREAD_SAFE_TYPES.contains(name)) {
                context.reportIssue(this, member, String.format(RULE_MESSAGE, name));
            }
        }
    }

    private boolean hasAnnotation(ClassTree clazz) {
        boolean hasAnnotation = false;
        for (AnnotationTree annotationTree : clazz.modifiers().annotations()) {
            String name = annotationTree.annotationType().symbolType().fullyQualifiedName();
            hasAnnotation |= VULNERABLE_ANNOTATIONS.contains(name);
        }
        return hasAnnotation;
    }

    private boolean extendsVulnerableClass(ClassTree clazz) {
        boolean extendsClass = false;
        TypeTree type = clazz.superClass();
        if (type != null) {
            String name = type.symbolType().fullyQualifiedName();
            extendsClass = VULNERABLE_CLASSES.contains(name);
        }
        return extendsClass;
    }

    private boolean implementsVulnerableInterface(ClassTree clazz) {
        boolean implementsInterface = false;
        for (TypeTree typeTree : clazz.superInterfaces()) {
            String name = typeTree.symbolType().fullyQualifiedName();
            implementsInterface |= VULNERABLE_INTERFACES.contains(name);
        }
        return implementsInterface;
    }

}

