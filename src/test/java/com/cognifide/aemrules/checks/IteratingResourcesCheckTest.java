package com.cognifide.aemrules.checks;

import org.junit.Test;

public class IteratingResourcesCheckTest extends AbstractBaseTest {

	@Test
	public void checkIteratingResources() {
		check = new IteratingResourcesCheck();
		filename = "src/test/files/checks/IteratingResourcesCheck.java";
		verify();
	}
}
