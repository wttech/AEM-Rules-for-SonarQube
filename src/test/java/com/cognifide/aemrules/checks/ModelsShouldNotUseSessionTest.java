package com.cognifide.aemrules.checks;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

/**
 * @author Krzysztof Watral
 */
public class ModelsShouldNotUseSessionTest extends AbstractBaseTest {

	@Rule
	public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

	@Test
	public void check() {
		check = new ModelsShouldNotUseSessionCheck();
		filename = "src/test/files/checks/SampleModel.java";
		verify();
	}
}
