package com.cognifide.aemrules.checks;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import java.util.Set;

import static org.sonar.plugins.java.api.tree.Tree.Kind.*;

@Rule(
		key = PreferSlingServletAnnotation.RULE_KEY,
		name = PreferSlingServletAnnotation.RULE_MESSAGE,
		priority = Priority.MINOR
)
public class PreferSlingServletAnnotation extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-8";

	public static final String RULE_MESSAGE = "Prefer cleaner @SlingServlet annotation.";

	public static final String PROPERTY_MESSAGE = "Property %s can be handled by @SlingServlet annotation.";

	public static final String SERVLET_RESOLVER_CONSTANTS_CLASS = "org.apache.sling.servlets.resolver.internal.ServletResolverConstants";

	private static final Set<String> SERVLET_CONSTANTS = Sets.newHashSet(
			"SLING_SERVLET_METHODS",
			"SLING_SERVLET_EXTENSIONS",
			"SLING_SERVLET_SELECTORS",
			"SLING_SERVLET_RESOURCE_TYPES");

	private static final Set<String> SERVLET_CONSTANTS_VALUES = Sets.newHashSet(
			"sling.servlet.paths",
			"sling.servlet.resourceTypes",
			"sling.servlet.selectors",
			"sling.servlet.extensions");
	public static final String NAME = "name";

	private JavaFileScannerContext context;

	private CheckAppliedAnnotationsVisitor annotations;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		gatherAppliedAnnotations(context);
		scan(context.getTree());
	}

	private void gatherAppliedAnnotations(JavaFileScannerContext context) {
		annotations = new CheckAppliedAnnotationsVisitor();
		context.getTree().accept(annotations);
	}

	@Override
	public void visitClass(ClassTree tree) {
		if (isSlingServlet(tree)) {
			scan(tree.modifiers());
			if (!annotations.hasSlingServletAnnotation()) {
				context.addIssue(tree, this, RULE_MESSAGE);
			} else if (annotations.hasMixedUpAnnotations()) {
				context.addIssue(tree, this, "@Component nor @Service annotation is not needed when @SlingServlet is used.");
			}
		}
	}

	@Override
	public void visitAnnotation(AnnotationTree annotationTree) {
		if (annotations.hasSlingServletAnnotation() && isPropertyAnnotation(annotationTree)) {
			for (ExpressionTree expression : annotationTree.arguments()) {
				if (isAssignmentToName(expression)) {
					checkIfPropertyInsteadSlingServletIsUsed(annotationTree, (AssignmentExpressionTree) expression);
				}
			}
		}
		super.visitAnnotation(annotationTree);
	}

	private void checkIfPropertyInsteadSlingServletIsUsed(AnnotationTree annotationTree, AssignmentExpressionTree assignmentExpression) {
		ExpressionTree expression = assignmentExpression.expression();
		if (expression.is(STRING_LITERAL)) {
			LiteralTree literal = (LiteralTree) expression;
			if (SERVLET_CONSTANTS_VALUES.contains(removeQuotes(literal.value()))) {
				String message = String.format(PROPERTY_MESSAGE, literal.value());
				context.addIssue(annotationTree, this, message);
			}
		} else if (expression.is(IDENTIFIER, MEMBER_SELECT)) {
			IdentifierTree identifier = expression.is(IDENTIFIER) ? (IdentifierTree) expression : ((MemberSelectExpressionTree) expression).identifier();
			String name = identifier.symbol().name();
			Type type = identifier.symbol().owner().type();
			if (type != null && type.fullyQualifiedName().equals(SERVLET_RESOLVER_CONSTANTS_CLASS) && SERVLET_CONSTANTS.contains(name)) {
				String message = String.format(PROPERTY_MESSAGE, name);
				context.addIssue(annotationTree, this, message);
			}
		}
	}

	private boolean isAssignmentToName(ExpressionTree expression) {
		boolean result = false;
		if (expression.is(Tree.Kind.ASSIGNMENT)) {
			AssignmentExpressionTree assignment = (AssignmentExpressionTree) expression;
			result = NAME.equals(((IdentifierTree) assignment.variable()).name());
		}
		return result;
	}

	private boolean isPropertyAnnotation(AnnotationTree annotationTree) {
		return isOfType(annotationTree, "org.apache.felix.scr.annotations.Property");
	}

	private boolean isOfType(AnnotationTree annotationTree, String fullyQualifiedName) {
		return annotationTree.annotationType().symbolType().fullyQualifiedName().equals(fullyQualifiedName);
	}

	private boolean isSlingServlet(ClassTree tree) {
		Type type = tree.symbol().type();
		boolean allMethodsServlet = type.isSubtypeOf("org.apache.sling.api.servlets.SlingAllMethodsServlet");
		boolean safeMethodsServlet = type.isSubtypeOf("org.apache.sling.api.servlets.SlingSafeMethodsServlet");
		return allMethodsServlet || safeMethodsServlet;
	}

	private String removeQuotes(String value) {
		return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
	}

	private class CheckAppliedAnnotationsVisitor extends BaseTreeVisitor {

		private boolean serviceAnnotation;

		private boolean componentAnnotation;

		private boolean slingServletAnnotation;

		@Override
		public void visitAnnotation(AnnotationTree annotationTree) {
			serviceAnnotation |= isOfType(annotationTree, "org.apache.felix.scr.annotations.Service");
			componentAnnotation |= isOfType(annotationTree, "org.apache.felix.scr.annotations.Component");
			slingServletAnnotation |= isOfType(annotationTree, "org.apache.felix.scr.annotations.sling.SlingServlet");
			super.visitAnnotation(annotationTree);
		}

		public boolean hasMixedUpAnnotations() {
			return slingServletAnnotation && (serviceAnnotation || componentAnnotation);
		}

		public boolean hasSlingServletAnnotation() {
			return slingServletAnnotation;
		}
	}
}
