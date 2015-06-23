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

public class ThreadSafeFieldCheckTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void checkThreadSafeFieldsInServlet() {
		ThreadSafeFieldCheck check = new ThreadSafeFieldCheck();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/ThreadSafeFieldCheckServlet.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(30).withMessage("Usage of org.apache.sling.api.resource.ResourceResolver as a field is not thread safe.")
				.next().atLine(32)
				.next().atLine(35)
				.next().atLine(37).withMessage("Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.")
				.next().atLine(39).withMessage("Usage of javax.jcr.Session as a field is not thread safe.");
	}

	@Test
	public void checkThreadSafeFieldsInFilter() {
		ThreadSafeFieldCheck check = new ThreadSafeFieldCheck();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/ThreadSafeFieldCheckFilter.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(21).withMessage("Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.");
	}
}
