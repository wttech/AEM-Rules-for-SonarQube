package com.cognifide.aemrules.checks;

import org.junit.Test;

public class UnsychronizedModificationCheckTest extends AbstractBaseTest {

	@Test
	public void unsychronizedModificationCheck() {
		check = new UnsychronizedModificationCheck();
		filename = "src/test/files/checks/UnsychronizedModificationCheck.java";
		verify();
	}

}