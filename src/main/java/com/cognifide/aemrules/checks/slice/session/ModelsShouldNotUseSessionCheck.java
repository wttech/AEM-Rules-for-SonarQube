package com.cognifide.aemrules.checks.slice.session;

import com.cognifide.aemrules.util.TypeUtils;
import javax.annotation.Nonnull;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.TypeTree;

import com.cognifide.aemrules.tag.Tags;
import java.util.Iterator;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.ModifierKeywordTree;

/**
 * @author Krzysztof Watral
 */
@Rule(
	key = ModelsShouldNotUseSessionCheck.RULE_KEY,
	name = ModelsShouldNotUseSessionCheck.RULE_MESSAGE,
	priority = Priority.BLOCKER,
	tags = {Tags.AEM, Tags.SLICE}
)
public class ModelsShouldNotUseSessionCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-9";

	public static final String RULE_MESSAGE
		= "Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().";

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
			implementedInitializableModel |= TypeUtils.isOfType(typeTree, INITIALIZABLE_MODEL_INTERFACE);
		}
		super.visitClass(classTree);
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (sliceAnnotationVisitor.hasSliceResourceAnnotation()
			&& !isConstructorOrAfterCreatedMethod(tree)
			&& !isPrivateMethod(tree)) {
			tree.accept(new MethodInvocationVisitor(this, context));
		}
		super.visitMethod(tree);
	}

	private boolean isConstructorOrAfterCreatedMethod(MethodTree tree) {
		return tree.is(MethodTree.Kind.CONSTRUCTOR)
			|| (implementedInitializableModel && "afterCreated".equals(tree.symbol().name()));
	}

	private boolean isPrivateMethod(MethodTree tree) {
		boolean result = false;
		for (Iterator<ModifierKeywordTree> iterator = tree.modifiers().modifiers().iterator(); iterator.hasNext() && !result;) {
			result = iterator.next().modifier() == Modifier.PRIVATE;
		}
		return result;
	}
}
