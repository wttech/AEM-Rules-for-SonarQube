package com.cognifide.aemrules.checks;

import org.junit.Test;

public class AnnotationsConstantsCheckTest extends AbstractBaseTest {

	@Test
	public void checkConstantsInAnnotations() {
		check = new AnnotationsConstantsCheck();
		filename = "src/test/files/checks/AnnotationsConstantsCheck.java";
		verify(false);
	}

}
