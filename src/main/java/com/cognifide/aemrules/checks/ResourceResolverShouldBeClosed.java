package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.visitors.CheckClosedVisitor;
import com.cognifide.aemrules.checks.visitors.FinallyBlockVisitor;
import com.cognifide.aemrules.checks.visitors.FindRRDeclarationVisitor;
import com.cognifide.aemrules.tag.Tags;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Collection;
import java.util.List;

@Rule(
	key = ResourceResolverShouldBeClosed.RULE_KEY,
	name = ResourceResolverShouldBeClosed.RULE_MESSAGE,
	priority = Priority.CRITICAL,
	tags = Tags.AEM
)
public class ResourceResolverShouldBeClosed extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-6";

	public static final String RULE_MESSAGE = "ResourceResolver should be closed in finally block.";

	protected JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext javaFileScannerContext) {
		context = javaFileScannerContext;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree method) {
		Collection<VariableTree> resolvers = findResolversInMethod(method);
		for (VariableTree injector : resolvers) {
			boolean closed = checkIfResourceResolverIsClosed(method, injector);
			if (!closed) {
				context.addIssue(injector, this, RULE_MESSAGE);
			}
		}
		super.visitMethod(method);
	}

	protected boolean checkIfResourceResolverIsClosed(MethodTree method, VariableTree injector) {
		List<IdentifierTree> usagesOfRR = injector.symbol().usages();
		CheckClosedVisitor checkClosedVisitor = new CheckClosedVisitor(usagesOfRR);
		FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkClosedVisitor);
		method.accept(finallyBlockVisitor);
		return checkClosedVisitor.isClosed();
	}

	protected Collection<VariableTree> findResolversInMethod(MethodTree methodTree) {
		FindRRDeclarationVisitor findVariableDeclarationVisitor = new FindRRDeclarationVisitor();
		methodTree.accept(findVariableDeclarationVisitor);
		return findVariableDeclarationVisitor.getDeclarations();
	}

}
