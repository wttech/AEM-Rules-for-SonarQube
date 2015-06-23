package com.cognifide.aemrules.checks;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import java.io.File;
import java.util.Set;

public class AnnotationsConstantsCheckTest {

    @Rule
    public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Test
    public void checkConstantsInAnnotations() {
        AnnotationsConstantsCheck check = new AnnotationsConstantsCheck();

        SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/AnnotationsConstantsCheck.java"), new VisitorsBridge(check));
        Set<CheckMessage> checkMessages = file.getCheckMessages();
        checkMessagesVerifier.verify(checkMessages)
                .next().atLine(24).withMessage(AnnotationsConstantsCheck.RULE_MESSAGE + " (class org.apache.sling.api.servlets.HttpConstants.METHOD_POST)")
                .next().atLine(25).withMessage(AnnotationsConstantsCheck.RULE_MESSAGE + " (interface org.osgi.framework.Constants.SERVICE_VENDOR)")
                .next().atLine(26).withMessage(AnnotationsConstantsCheck.RULE_MESSAGE + " (interface org.osgi.framework.Constants.SERVICE_DESCRIPTION)")
                .next().atLine(36).withMessage(AnnotationsConstantsCheck.RULE_MESSAGE + " (interface org.osgi.framework.Constants.SERVICE_DESCRIPTION)");
    }


}
