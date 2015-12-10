package com.cognifide.aemrules.checks;

import java.io.File;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

/**
 * @author Krzysztof Watral
 */
public class ModelsShouldNotUseSessionTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void check() {
		ModelsShouldNotUseSessionCheck check = new ModelsShouldNotUseSessionCheck();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner
				.scanSingleFile(new File("src/test/files/checks/SampleModel.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(34).withMessage(ModelsShouldNotUseSessionCheck.RULE_MESSAGE)
				.next().atLine(38);
	}

}
