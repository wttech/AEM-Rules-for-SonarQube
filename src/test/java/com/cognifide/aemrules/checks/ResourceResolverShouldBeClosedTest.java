package com.cognifide.aemrules.checks;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import java.io.File;
import java.util.Set;

public class ResourceResolverShouldBeClosedTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void checkInjectorNotClosedInFinallyBlock() {
		ResourceResolverShouldBeClosed check = new ResourceResolverShouldBeClosed();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/SampleServlet.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(31).withMessage("ResourceResolver should be closed in finally block.")
				.next().atLine(82);
	}

}
