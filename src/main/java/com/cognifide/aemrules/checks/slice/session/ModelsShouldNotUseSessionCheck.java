package com.cognifide.aemrules.checks.slice.session;

import javax.annotation.Nonnull;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.TypeTree;

import com.cognifide.aemrules.tag.Tags;

/**
 * @author Krzysztof Watral
 */
@Rule(
	key = ModelsShouldNotUseSessionCheck.RULE_KEY,
	name = ModelsShouldNotUseSessionCheck.RULE_MESSAGE,
	priority = Priority.BLOCKER,
	tags = Tags.AEM
)
public class ModelsShouldNotUseSessionCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-9";

	public static final String RULE_MESSAGE
		= "Objects annotated by @SliceResource should not use or return any session based object. (except: constructor, com.cognifide.slice.api.model.InitializableModel.afterCreated())";

	private static final String INITIALIZABLE_MODEL_INTERFACE = "com.cognifide.slice.api.model.InitializableModel";

	private JavaFileScannerContext context;

	private SliceAnnotationVisitor sliceAnnotationVisitor;

	private boolean implementedInitializableModel;

	@Override
	public void scanFile(@Nonnull JavaFileScannerContext context) {
		this.context = context;
		sliceAnnotationVisitor = new SliceAnnotationVisitor();
		context.getTree().accept(sliceAnnotationVisitor);
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree classTree) {
		for (TypeTree typeTree : classTree.superInterfaces()) {
			implementedInitializableModel |= isOfType(typeTree, INITIALIZABLE_MODEL_INTERFACE);
		}
		super.visitClass(classTree);
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (sliceAnnotationVisitor.hasSliceResourceAnnotation() && isRegularMethod(tree)) {
			checkSessionUsage(tree);
		}
		super.visitMethod(tree);
	}

	private boolean isRegularMethod(MethodTree tree) {
		boolean constructor = tree.is(MethodTree.Kind.CONSTRUCTOR);
		boolean afterCreated = implementedInitializableModel && "afterCreated".equals(tree.symbol().name());
		return !constructor && !afterCreated;
	}

	private void checkSessionUsage(MethodTree tree) {
		SessionUsageVisitor sessionUsageVisitor = new SessionUsageVisitor();
		tree.accept(sessionUsageVisitor);

		if (null != sessionUsageVisitor.getReturnStatementTree()) {
			context.reportIssue(this, sessionUsageVisitor.getReturnStatementTree(), RULE_MESSAGE);
		}

		for (MemberSelectExpressionTree sessionMember : sessionUsageVisitor.getSessionMemberSelect()) {
			context.reportIssue(this, sessionMember, RULE_MESSAGE);
		}
	}

	private boolean isOfType(final TypeTree typeTree, final String fullyQualifiedName) {
		return typeTree.symbolType().is(fullyQualifiedName);
	}

}
