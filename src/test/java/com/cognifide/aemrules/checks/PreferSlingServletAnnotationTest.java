package com.cognifide.aemrules.checks;

import org.junit.Test;

public class PreferSlingServletAnnotationTest extends AbstractBaseTest {

	@Test
	public void checkLackOfAnnotation() {
		check = new PreferSlingServletAnnotation();
		filename = "src/test/files/checks/slingservlet/SlingServletOne.java";
		verify();
	}

	@Test
	public void checkMixedAnnotations() {
		check = new PreferSlingServletAnnotation();
		filename = "src/test/files/checks/slingservlet/SlingServletTwo.java";
		verify();
	}

	@Test
	public void checkRedundantProperties() {
		check = new PreferSlingServletAnnotation();
		filename = "src/test/files/checks/slingservlet/SlingServletThree.java";
		verify();
	}

}
