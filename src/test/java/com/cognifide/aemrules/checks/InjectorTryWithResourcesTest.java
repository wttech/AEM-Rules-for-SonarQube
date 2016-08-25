package com.cognifide.aemrules.checks;

import org.junit.Test;

public class InjectorTryWithResourcesTest extends AbstractBaseTest {

	@Test
	public void checkInjectorUsesTryWithResourcesBlock() {
		check = new InjectorTryWithResourcesCheck();
		filename = "src/test/files/checks/injectors/AutoclosableInjector.java";
		verify();
	}

}
