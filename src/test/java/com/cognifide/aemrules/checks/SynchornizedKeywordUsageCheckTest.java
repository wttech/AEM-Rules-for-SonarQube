package com.cognifide.aemrules.checks;

import org.junit.Test;

public class SynchornizedKeywordUsageCheckTest extends AbstractBaseTest {

	@Test
	public void checkThreadSafeFieldsInServlet() {
		check = new SynchornizedKeywordUsageCheck();
		filename = "src/test/files/checks/SynchornizedKeywordUsageCheck.java";
		verify();
	}

}
