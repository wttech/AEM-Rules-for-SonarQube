package com.cognifide.aemrules.checks.slingmodels;

import com.cognifide.aemrules.tag.Tags;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.Arguments;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

@Rule(
	key = DefaultInjectionStrategyAnnotationCheck.RULE_KEY,
	name = DefaultInjectionStrategyAnnotationCheck.RULE_MESSAGE,
	priority = Priority.MINOR,
	tags = {Tags.AEM, Tags.SLING_MODELS})
public class DefaultInjectionStrategyAnnotationCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-16";

	public static final String RULE_MESSAGE = "Optional is defined as DefaultInjectionStrategy";

	private static final String OPTIONAL_ANNOTATION_FULL_NAME = "org.apache.sling.models.annotations.Optional";

	private static final String MODEL_ANNOTATION_FULL_NAME = "org.apache.sling.models.annotations.Model";

	private static final String DEFAULT_INJECTION_STRATEGY = "defaultInjectionStrategy";

	private static final String OPTIONAL = "OPTIONAL";

	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		List<AnnotationTree> annotations = tree.modifiers().annotations();
		for (AnnotationTree annotationTree : annotations) {
			if (isSearchedAnnotation(annotationTree, MODEL_ANNOTATION_FULL_NAME)
					&& isOptionalDefaultValue(tree, annotationTree.arguments())) {
				CheckIfOptionalAnnotationIsPresent checkIfOptionalIsPresent = new CheckIfOptionalAnnotationIsPresent(
						this);
				tree.accept(checkIfOptionalIsPresent);
			}
		}
		super.visitClass(tree);
	}

	private boolean isOptionalDefaultValue(ClassTree tree, Arguments arguments) {
		boolean optionalIsDefaultValue = false;
		for (ExpressionTree argument : arguments) {
			if (argument.is(Tree.Kind.ASSIGNMENT)) {
				AssignmentExpressionTree assignment = (AssignmentExpressionTree) argument;
				if (isDefaultInjectionStrategyAssignment(assignment)) {
					optionalIsDefaultValue = isOptionalStrategyValue(assignment);
				}
			}
		}
		return optionalIsDefaultValue;
	}

	private boolean isOptionalStrategyValue(AssignmentExpressionTree assignment) {
		return OPTIONAL.equals(((MemberSelectExpressionTree) assignment.expression()).identifier().name());
	}

	private boolean isDefaultInjectionStrategyAssignment(AssignmentExpressionTree assignment) {
		return DEFAULT_INJECTION_STRATEGY.equals(((IdentifierTree) assignment.variable()).name());
	}

	private boolean isSearchedAnnotation(AnnotationTree annotationTree, String fullyQualifiedName) {
		return annotationTree.annotationType().symbolType().fullyQualifiedName().equals(fullyQualifiedName);
	}

	private class CheckIfOptionalAnnotationIsPresent extends BaseTreeVisitor {

		private final JavaFileScanner scanner;

		CheckIfOptionalAnnotationIsPresent(DefaultInjectionStrategyAnnotationCheck scanner) {
			this.scanner = scanner;
		}

		@Override
		public void visitAnnotation(AnnotationTree annotationTree) {
			if (isSearchedAnnotation(annotationTree, OPTIONAL_ANNOTATION_FULL_NAME)) {
				context.reportIssue(scanner, annotationTree, RULE_MESSAGE);
			}
			super.visitAnnotation(annotationTree);
		}
	}

}
