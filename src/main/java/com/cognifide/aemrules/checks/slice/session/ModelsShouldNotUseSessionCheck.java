package com.cognifide.aemrules.checks.slice.session;

import com.cognifide.aemrules.tag.Tags;
import javax.annotation.Nonnull;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

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
		= "Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().";

	private JavaFileScannerContext context;

	@Override
	public void scanFile(@Nonnull JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		tree.accept(new ClassVisitor(context, this));
	}

}
