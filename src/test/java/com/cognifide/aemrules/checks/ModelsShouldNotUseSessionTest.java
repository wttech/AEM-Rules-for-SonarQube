package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.slice.session.ModelsShouldNotUseSessionCheck;
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
		filename = "src/test/files/checks/session/SampleModel.java";
		verify();
	}

	@Test
	public void checkPrivateMethodUsage() {
		check = new ModelsShouldNotUseSessionCheck();
		filename = "src/test/files/checks/session/ModelWithSessionLeak.java";
		verify();
	}
}
