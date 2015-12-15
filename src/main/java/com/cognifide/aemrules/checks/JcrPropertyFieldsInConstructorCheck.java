package com.cognifide.aemrules.checks;

import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.declaration.MethodTreeImpl;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import com.google.common.collect.Sets;

@Rule(
		key = JcrPropertyFieldsInConstructorCheck.RULE_KEY,
		name = JcrPropertyFieldsInConstructorCheck.RULE_MESSAGE,
		priority = Priority.MINOR
)
public class JcrPropertyFieldsInConstructorCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-12";

	public static final String RULE_MESSAGE = "Fields annotated by @JcrProperty shouldn't be access from constructor.";

	private JavaFileScannerContext context;

	private CheckClassVisitor classVisitor;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		classVisitor = new CheckClassVisitor();
		context.getTree().accept(classVisitor);
		scan(context.getTree());
	}

	private Set<String> methodParams(MethodTree tree) {
		Set<String> params = Sets.newHashSet();
		for (VariableTree var : tree.parameters()) {
			params.add(var.symbol().name());
		}
		return params;
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (isConstructor(tree)) {
			final Set<String> methodParams = methodParams(tree);
			final Set<Tree> errors = Sets.newHashSet();

			tree.accept(new BaseTreeVisitor() {
				@Override
				public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
					if (classVisitor.annotatedVariables.contains(tree.identifier().symbol().name())) {
						errors.add(tree);
					}
					super.visitMemberSelectExpression(tree);
				}

				@Override
				public void visitIdentifier(IdentifierTree tree) {
					if (tree.symbol().isVariableSymbol()) {
						if (!methodParams.contains(tree.symbol().name())) {
							if (classVisitor.annotatedVariables.contains(tree.symbol().name())) {
								errors.add(tree);
							}
						}
					}

					super.visitIdentifier(tree);
				}
			});

			for (Tree error : errors) {
				context.addIssue(error, this, RULE_MESSAGE);
			}
		}

		super.visitMethod(tree);
	}

	public boolean isConstructor(MethodTree tree) {
		return ((MethodTreeImpl) tree).getGrammarRuleKey() == MethodTree.Kind.CONSTRUCTOR;
	}

	private boolean isOfType(final TypeTree typeTree, final String fullyQualifiedName) {
		return typeTree.symbolType().fullyQualifiedName().equals(fullyQualifiedName);
	}

	private class CheckClassVisitor extends BaseTreeVisitor {

		private final Set<String> annotatedVariables = Sets.newHashSet();

		@Override
		public void visitClass(ClassTree classTree) {
			classTree.accept(new BaseTreeVisitor() {
				@Override
				public void visitVariable(VariableTree tree) {
					CheckAppliedAnnotationsVisitor visitor = new CheckAppliedAnnotationsVisitor();
					tree.accept(visitor);

					if (visitor.hasJcrPropertyAnnotation()) {
						annotatedVariables.add(tree.symbol().name());
					}
					super.visitVariable(tree);
				}
			});
			super.visitClass(classTree);
		}

		private class CheckAppliedAnnotationsVisitor extends BaseTreeVisitor {

			private static final String JCR_PROPERTY_ANNOTATION = "com.cognifide.slice.mapper.annotation.JcrProperty";

			private boolean jcrPropertyAnnotation;

			@Override
			public void visitAnnotation(AnnotationTree annotationTree) {
				jcrPropertyAnnotation |= isOfType(annotationTree.annotationType(), JCR_PROPERTY_ANNOTATION);
				super.visitAnnotation(annotationTree);
			}

			public boolean hasJcrPropertyAnnotation() {
				return jcrPropertyAnnotation;
			}
		}
	}
}
