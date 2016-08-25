package com.cognifide.aemrules.checks;

import org.junit.Test;

public class InjectorShouldBeClosedTest extends AbstractBaseTest {

	@Test
	public void checkInjectorNotClosedInFinallyBlock() {
		check = new InjectorShouldBeClosedCheck();
		filename = "src/test/files/checks/injectors/ClosingInjector.java";
		verify();
	}

}
