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
package com.cognifide.aemrules;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

public class AemRulesSonarPluginTest {

    @Test
    public void webPluginTester() {
        Plugin.Context context = new Plugin.Context(SonarRuntimeImpl.forSonarQube(Version.create(6, 7), SonarQubeSide.SERVER));

        new AemRulesSonarPlugin().define(context);
        assertThat(context.getExtensions()).hasSize(7);
    }

}