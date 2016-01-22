package com.cognifide.aemrules.checks;

import org.junit.Test;

public class UnsynchronizedModificationCheckTest extends AbstractBaseTest {

	@Test
	public void unsychronizedModificationCheck() {
		check = new UnsynchronizedModificationCheck();
		filename = "src/test/files/checks/UnsychronizedModificationCheck.java";
		verify();
	}

}