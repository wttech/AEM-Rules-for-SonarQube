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

# The env variables below are implicitly created for the invoking GitHub action's input parameters
# See https://docs.github.com/en/actions/creating-actions/metadata-syntax-for-github-actions#example-specifying-inputs
ARG inputs_sonarqube_base_image
ARG inputs_aem_rules_binary

FROM $inputs_sonarqube_base_image

COPY $inputs_aem_rules_binary $SONARQUBE_HOME/extensions/plugins/$inputs_aem_rules_binary
# COPY sonar-findbugs-plugin-3.11.0.jar $SONARQUBE_HOME/extensions/plugins/sonar-findbugs-plugin-3.11.0.jar