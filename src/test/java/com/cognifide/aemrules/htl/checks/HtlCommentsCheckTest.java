
package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.AbstractBaseTest;
import org.junit.Test;

public class HtlCommentsCheckTest extends AbstractBaseTest {

  @Test
  public void checkHTLStyleOfCommenting() {
    check = new HtlCommentsCheck();
    filename = "src/test/files/checks/htl/HtlCommentsCheck.html";
    verify();
  }
} 