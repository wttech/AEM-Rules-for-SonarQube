package com.cognifide.aemrules.checks;

import com.cognifide.aemrules.checks.slice.iterator.IteratingResourcesCheck;
import org.junit.Test;

public class ContentResourceCheckTest extends AbstractBaseTest  {

  @Test
  public void checkIfContentResourceIsNotNull() {
    check = new ContentResourceCheck();
    filename = "src/test/files/checks/ContentResourceCheck.java";
    verify();
  }
}
