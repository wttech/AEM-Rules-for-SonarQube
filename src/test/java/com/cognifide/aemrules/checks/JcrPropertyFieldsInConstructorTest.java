package com.cognifide.aemrules.checks;

import org.junit.Test;

public class JcrPropertyFieldsInConstructorTest extends AbstractBaseTest {

	@Test
	public void checkLackOfAnnotation() {
		check = new JcrPropertyFieldsInConstructorCheck();
		filename = "src/test/files/checks/JcrPropertySampleModel.java";
		verify();
	}


}
