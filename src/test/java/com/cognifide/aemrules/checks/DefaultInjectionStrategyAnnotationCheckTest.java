package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.slingmodels.DefaultInjectionStrategyAnnotationCheck;
import org.junit.Test;

public class DefaultInjectionStrategyAnnotationCheckTest extends AbstractBaseTest  {

	@Test
	public void checkAnnotation() {
		check = new DefaultInjectionStrategyAnnotationCheck();
		filename = "src/test/files/checks/slingmodels/DefaultInjectionStrategyAnnotationCheck.java";
		verify();
	}

}
