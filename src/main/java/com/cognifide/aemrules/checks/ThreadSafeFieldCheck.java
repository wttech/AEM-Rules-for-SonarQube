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
import com.cognifide.aemrules.version.AemVersion;
import com.google.common.collect.Sets;
import java.util.Set;
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
public class ThreadSafeFieldCheck extends BaseTreeVisitor implements JavaFileScanner {

    public static final String RULE_NAME = "Non-thread safe object used as a field of Servlet / Filter etc.";

    public static final String RULE_KEY = "AEM-3";

    public static final String RULE_MESSAGE = "Usage of %s as a field is not thread safe.";

    private static Set<String> vulnerableClasses = Sets.newHashSet(
        // empty for now
    );

    private static Set<String> vulnerableInterfaces = Sets.newHashSet(
        "javax.servlet.Servlet",
        "javax.servlet.Filter",
        "org.osgi.service.event.EventHandler"
    );

    private static Set<String> vulnerableAnnotations = Sets.newHashSet(
        "org.apache.felix.scr.annotations.Component",
        "org.osgi.service.component.annotations.Component",
        "org.apache.felix.scr.annotations.sling.SlingServlet", // this is possibly duplicative, but that shouldn't be a problem.
        "org.apache.felix.scr.annotations.sling.SlingFilter" // this is possibly duplicative, but that shouldn't be a problem.
    );

    private static Set<String> nonThreadSafeTypes = Sets.newHashSet(
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
        "org.apache.jackrabbit.api.security.user.UserManager");

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
            if (nonThreadSafeTypes.contains(name)) {
                context.reportIssue(this, member, String.format(RULE_MESSAGE, name));
            }
        }
    }

    private boolean hasAnnotation(ClassTree clazz) {
        boolean hasAnnotation = false;
        for (AnnotationTree annotationTree : clazz.modifiers().annotations()) {
            String name = annotationTree.annotationType().symbolType().fullyQualifiedName();
            hasAnnotation |= vulnerableAnnotations.contains(name);
        }
        return hasAnnotation;
    }

    private boolean extendsVulnerableClass(ClassTree clazz) {
        boolean extendsClass = false;
        TypeTree type = clazz.superClass();
        if (type != null) {
            String name = type.symbolType().fullyQualifiedName();
            extendsClass = vulnerableClasses.contains(name);
        }
        return extendsClass;
    }

    private boolean implementsVulnerableInterface(ClassTree clazz) {
        boolean implementsInterface = false;
        for (TypeTree typeTree : clazz.superInterfaces()) {
            String name = typeTree.symbolType().fullyQualifiedName();
            implementsInterface |= vulnerableInterfaces.contains(name);
        }
        return implementsInterface;
    }

}

