package com.cognifide.aemrules.checks;

import org.junit.Test;

public class ModifiableValueMapUsageCheckTest extends AbstractBaseTest{

	@Test
	public void checkIfMVMIsUsedToRetrievePropertiesFromResource(){
		check = new ModifiableValueMapUsageCheck();
		filename = "src/test/files/checks/ModifiableValueMapUsageCheck.java";
		verify();
	}
}
