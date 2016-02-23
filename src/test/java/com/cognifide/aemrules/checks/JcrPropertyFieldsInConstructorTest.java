package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.slice.jcrproperty.JcrPropertyFieldsInConstructorCheck;
import org.junit.Test;

public class JcrPropertyFieldsInConstructorTest extends AbstractBaseTest {

	@Test
	public void checkLackOfAnnotation() {
		check = new JcrPropertyFieldsInConstructorCheck();
		filename = "src/test/files/checks/jcrproperty/JcrPropertySampleModel.java";
		verify();
	}

	@Test
	public void checkModelWithoutJcrPropertyAnnotation() {
		check = new JcrPropertyFieldsInConstructorCheck();
		filename = "src/test/files/checks/jcrproperty/NoJcrPropertyModel.java";
		verifyNoIssues();
	}


}
