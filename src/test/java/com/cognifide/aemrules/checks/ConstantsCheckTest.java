package com.cognifide.aemrules.checks;

import org.junit.Test;

public class ConstantsCheckTest extends AbstractBaseTest {

	@Test
	public void checkConstants() {
		check = new ConstantsCheck();
		filename = "src/test/files/checks/ConstantsCheck.java";
		verify(false);
	}
}
