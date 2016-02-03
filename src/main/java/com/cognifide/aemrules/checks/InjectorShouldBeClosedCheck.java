package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.visitors.CheckClosedVisitor;
import com.cognifide.aemrules.checks.visitors.FinallyBlockVisitor;
import com.cognifide.aemrules.checks.visitors.FindVariableDeclarationVisitor;
import com.cognifide.aemrules.tag.Tags;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;

@Rule(
		key = InjectorShouldBeClosedCheck.RULE_KEY,
		name = InjectorShouldBeClosedCheck.RULE_MESSAGE,
		priority = Priority.CRITICAL,
		tags = Tags.AEM
)
public class InjectorShouldBeClosedCheck extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-4";

	public static final String RULE_MESSAGE = "Injector should be closed in finally block or created as a resource within try block.";

	public static final String CLASS_INJECTOR_NAME = "com.cognifide.slice.api.injector.InjectorWithContext";

	protected JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext javaFileScannerContext) {
		context = javaFileScannerContext;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree method) {
		List<VariableTree> injectors = findInjectorsInMethod(method);
		for (VariableTree injector : injectors) {
			boolean closed = checkIfInjectorIsClosedInMethod(method, injector);
			if (!closed) {
				context.addIssue(injector, this, RULE_MESSAGE);
			}
		}
		super.visitMethod(method);
	}

	protected boolean checkIfInjectorIsClosedInMethod(MethodTree method, VariableTree injector) {
		List<IdentifierTree> usagesOfInjector = injector.symbol().usages();
		CheckClosedVisitor checkClosedVisitor = new CheckClosedVisitor(usagesOfInjector);
		FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkClosedVisitor);
		method.accept(finallyBlockVisitor);
		return checkClosedVisitor.isClosed();
	}

	protected List<VariableTree> findInjectorsInMethod(MethodTree methodTree) {
		FindVariableDeclarationVisitor varVisitor = new FindVariableDeclarationVisitor(CLASS_INJECTOR_NAME);
		methodTree.accept(varVisitor);
		return varVisitor.getDeclarations();
	}

}
