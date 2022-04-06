#
# #%L
# AEM Rules for SonarQube
# %%
# Copyright (C) 2015-2019 Wunderman Thompson Technology
# %%
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# #L%
#

FROM sonarqube:8.9-community

# FIXME: Make this use versions from environment variables
COPY sonar-aemrules-plugin-1.2.jar $SONARQUBE_HOME/extensions/plugins/sonar-aemrules-plugin-1.2.jar
COPY sonar-findbugs-plugin-3.11.0.jar $SONARQUBE_HOME/extensions/plugins/sonar-findbugs-plugin-3.11.0.jar