package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.util.ConstantsChecker;
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
				context.addIssue(tree, this, String.format("%s (%s)", RULE_MESSAGE, ConstantsChecker.getAnnotationConstantFieldName(literalValue)));
			}
		}
		super.visitLiteral(tree);
	}

	private String removeQuotes(String value) {
		return value.replaceAll("^\"|\"$", StringUtils.EMPTY);
	}
}
