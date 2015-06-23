package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.visitors.CheckLoggedOutVisitor;
import com.cognifide.aemrules.checks.visitors.FinallyBlockVisitor;
import com.cognifide.aemrules.checks.visitors.FindSessionDeclarationVisitor;
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
		key = SessionShouldBeLoggedOut.RULE_KEY,
		name = SessionShouldBeLoggedOut.RULE_MESSAGE,
		priority = Priority.CRITICAL
)
public class SessionShouldBeLoggedOut extends BaseTreeVisitor implements JavaFileScanner {

	public static final String RULE_KEY = "AEM-7";

	public static final String RULE_MESSAGE = "Session should be logged out in finally block.";

	protected JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext javaFileScannerContext) {
		context = javaFileScannerContext;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree method) {
		Collection<VariableTree> sessions = findSessionsInMethod(method);
		for (VariableTree session : sessions) {
			boolean closed = checkIfLoggedOut(method, session);
			if (!closed) {
				context.addIssue(session, this, RULE_MESSAGE);
			}
		}
		super.visitMethod(method);
	}

	protected boolean checkIfLoggedOut(MethodTree method, VariableTree injector) {
		List<IdentifierTree> usagesOfSession = injector.symbol().usages();
		CheckLoggedOutVisitor checkLoggedOutVisitor = new CheckLoggedOutVisitor(usagesOfSession);
		FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkLoggedOutVisitor);
		method.accept(finallyBlockVisitor);
		return checkLoggedOutVisitor.isLoggedOut();
	}

	protected Collection<VariableTree> findSessionsInMethod(MethodTree methodTree) {
		FindSessionDeclarationVisitor findSessionDeclarationVisitor = new FindSessionDeclarationVisitor();
		methodTree.accept(findSessionDeclarationVisitor);
		return findSessionDeclarationVisitor.getDeclarations();
	}

}
