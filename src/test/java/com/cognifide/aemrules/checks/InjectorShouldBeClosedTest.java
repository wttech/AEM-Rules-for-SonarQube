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

public class InjectorShouldBeClosedTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void checkInjectorNotClosedInFinallyBlock() {
		InjectorShouldBeClosedCheck check = new InjectorShouldBeClosedCheck();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/ClosingInjector.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(18).withMessage("Injector should be closed in finally block or created as a resource within try block.")
				.next().atLine(19)
				.next().atLine(25)
				.next().atLine(35)
				.next().atLine(42)
				.next().atLine(57)
				.next().atLine(72)
				.next().atLine(81);
	}

}
