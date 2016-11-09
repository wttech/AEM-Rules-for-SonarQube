/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
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
package com.cognifide.aemrules.checks.slingquery;

import com.cognifide.aemrules.checks.InjectorShouldBeClosedCheck;
import com.cognifide.aemrules.tag.Tags;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.MethodTree;

@Rule(
	key = SlingQueryImplicitStrategyCheck.RULE_KEY,
	name = SlingQueryImplicitStrategyCheck.RULE_MESSAGE,
	priority = Priority.INFO,
	tags = {Tags.AEM}
)
public class SlingQueryImplicitStrategyCheck extends InjectorShouldBeClosedCheck {

	protected static final String RULE_KEY = "AEM-18";

	protected static final String RULE_MESSAGE = "Implicit search strategy used in Sling Query";

	@Override
	public void visitMethod(MethodTree method) {
		super.visitMethod(method);
	}

}
