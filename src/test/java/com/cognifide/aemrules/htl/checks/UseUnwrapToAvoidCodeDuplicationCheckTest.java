package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.AbstractBaseTest;
import org.junit.Test;

public class UseUnwrapToAvoidCodeDuplicationCheckTest extends AbstractBaseTest {

    @Test
    public void test() {
        check = new UseUnwrapToAvoidCodeDuplicationCheck();
        filename = "src/test/files/checks/htl/UseUnwrapToAvoidCodeDuplicationCheck.html";
        verify();
    }
}