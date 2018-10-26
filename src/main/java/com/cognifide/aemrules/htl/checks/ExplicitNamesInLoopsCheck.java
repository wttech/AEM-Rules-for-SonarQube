package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.visitors.DefaultHtlVisitor;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.version.AemVersion;
import org.sonar.check.Rule;

@AemVersion(
    all = true
)
@Metadata(
    technicalDebt = "10min"
)
@Rule(
)

public class ExplicitNamesInLoopsCheck extends DefaultHtlVisitor {

}
