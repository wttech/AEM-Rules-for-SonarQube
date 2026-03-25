====
    #%L
    AEM Rules for SonarQube
    %%
    Copyright (C) 2015-2024 VML
    %%
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    #L%
====

One HTML file per AEM HTL rule (repository AEM-HTL, keys HTL-0 … HTL-16).
Each file is intentionally non-compliant for that rule.
When you add or remove an HTL check, update HtlRulesList in the plugin and add or remove
the matching HTL-<n>.html here (one intentional violation per rule key).
After scripts/smoke-aem-htl-plugin.sh, scripts/verify-aem-htl-smoke-issues.py checks the
Issues API for ≥1 open issue per AEM-HTL:HTL-0..HTL-16 and per AEM-JAVA key hardcoded there
(same set as JavaRulesList.getJavaChecks()).
