package com.cognifide.aemrules.checks;

import org.junit.Test;

public class HttpConstantCheckTest extends AbstractBaseTest {

	@Test
	public void visitNode() {
		check = new HttpConstantCheck();
		filename = "src/test/files/checks/HttpConstantCheck.java";
		verify(false);
	}

}
