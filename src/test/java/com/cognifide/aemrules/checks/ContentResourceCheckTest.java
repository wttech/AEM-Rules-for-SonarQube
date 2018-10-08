package com.cognifide.aemrules.checks;

import org.junit.Test;

public class ContentResourceCheckTest extends AbstractBaseTest  {

  @Test
  public void checkIfContentResourceIsNotNull() {
    check = new ContentResourceCheck();
    filename = "src/test/files/checks/ContentResourceCheck.java";
    verify();
  }
}
