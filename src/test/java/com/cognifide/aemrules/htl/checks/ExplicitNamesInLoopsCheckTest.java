package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.AbstractBaseTest;
import org.junit.Test;

public class ExplicitNamesInLoopsCheckTest extends AbstractBaseTest {

    @Test
    public void checkHtlAttributesOrder() {
        check = new HtlAttributesShouldBeAtTheEndCheck();
        filename = "src/test/files/checks/htl/HtlAttributesShouldBeAtTheEndCheck.html";
        verify();
    }
}
