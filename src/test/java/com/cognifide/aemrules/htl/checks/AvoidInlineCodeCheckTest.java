package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.AbstractBaseTest;
import org.junit.Test;

public class AvoidInlineCodeCheckTest extends AbstractBaseTest {

    @Test
    public void checkInlineCode() {
        check = new AvoidInlineCodeCheck();
        filename = "src/test/files/checks/htl/AvoidInlineCodeCheck.html";
        verify();
    }
}