package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.AbstractBaseTest;
import org.junit.Test;

public class ExplicitNamesInLoopsCheckTest extends AbstractBaseTest {

  @Test
  public void ExplicitNamesInLoopsCheck() {
    check = new ExplicitNamesInLoopsCheck();
    filename = "src/test/files/checks/htl/ExplicitNamesInLoopsCheck.html";
    verify();
  }
}