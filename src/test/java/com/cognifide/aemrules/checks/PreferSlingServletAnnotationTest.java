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

public class PreferSlingServletAnnotationTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void checkLackOfAnnotation() {
		PreferSlingServletAnnotation check = new PreferSlingServletAnnotation();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/slingservlet/SlingServletOne.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(21).withMessage("Prefer cleaner @SlingServlet annotation.");
	}

	@Test
	public void checkMixedAnnotations() {
		PreferSlingServletAnnotation check = new PreferSlingServletAnnotation();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/slingservlet/SlingServletTwo.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(21).withMessage("@Component nor @Service annotation is not needed when @SlingServlet is used.");
	}

	@Test
	public void checkRedundantProperties() {
		PreferSlingServletAnnotation check = new PreferSlingServletAnnotation();
		VisitorsBridge visitorsBridge = new VisitorsBridge(check, TestClasspath.CLASSPATH_JAR);
		SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/slingservlet/SlingServletThree.java"), visitorsBridge);
		Set<CheckMessage> checkMessages = file.getCheckMessages();
		checkMessagesVerifier.verify(checkMessages)
				.next().atLine(22).withMessage("Property SLING_SERVLET_SELECTORS can be handled by @SlingServlet annotation.")
				.next().atLine(23).withMessage("Property SLING_SERVLET_EXTENSIONS can be handled by @SlingServlet annotation.")
				.next().atLine(24).withMessage("Property \"sling.servlet.resourceTypes\" can be handled by @SlingServlet annotation.");
	}

}
