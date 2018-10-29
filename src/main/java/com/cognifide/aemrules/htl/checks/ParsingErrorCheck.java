/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2018 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.htl.api.ParsingErrorRule;
import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
    key = ParsingErrorCheck.RULE_KEY,
    name = ParsingErrorCheck.RULE_NAME,
    priority = Priority.MINOR,
    tags = Tags.AEM
)
@AemVersion(
    from = "6.0"
)
@Metadata(
    technicalDebt = "5min"
)
@ParsingErrorRule
public class ParsingErrorCheck extends AbstractHtlCheck {

    static final String RULE_KEY = "HTL-0";

    static final String RULE_NAME = "Compilation error";
}
