package com.cognifide.aemrules.checks;

import org.junit.Test;

public class SessionShouldBeLoggedOutTest extends AbstractBaseTest {

	@Test
	public void checkInjectorNotClosedInFinallyBlock() {
		check = new SessionShouldBeLoggedOut();
		filename = "src/test/files/checks/SessionLogout.java";
		verify();
	}

}
