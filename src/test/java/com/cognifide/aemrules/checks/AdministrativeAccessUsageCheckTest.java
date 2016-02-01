package com.cognifide.aemrules.checks;

import org.junit.Test;

public class AdministrativeAccessUsageCheckTest extends AbstractBaseTest {

	@Test
	public void administrativeAccessUsageCheck() {
		check = new AdministrativeAccessUsageCheck();
		filename = "src/test/files/checks/AdministrativeAccessUsageCheck.java";
		verify();
	}

}
