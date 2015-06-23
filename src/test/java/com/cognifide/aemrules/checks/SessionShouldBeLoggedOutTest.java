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

public class SessionShouldBeLoggedOutTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void checkInjectorNotClosedInFinallyBlock() {
		SessionShouldBeLoggedOut check = new SessionShouldBeLoggedOut();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/SessionLogout.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(54).withMessage("Session should be logged out in finally block.")
				.next().atLine(59)
				.next().atLine(72)
				.next().atLine(85);
	}

}
