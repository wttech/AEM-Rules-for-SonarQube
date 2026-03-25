#!/usr/bin/env bash
#
# #%L
# AEM Rules for SonarQube
# %%
# Copyright (C) 2015-2024 VML
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

# Run e2e-aem-htl-smoke against SonarQube, then assert every AEM-HTL + AEM-JAVA smoke rule has ≥1 open issue.
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

export SMOKE_ISSUES_HOST="${SMOKE_ISSUES_HOST:-http://localhost:9000}"
export SMOKE_ISSUES_TOKEN="${SONAR_TOKEN:-${SMOKE_ISSUES_TOKEN:-}}"
export SMOKE_ISSUES_PROJECT_KEY="${SMOKE_ISSUES_PROJECT_KEY:-aem-htl-smoke-local}"
export SMOKE_ISSUES_REPORT_TASK="${SMOKE_ISSUES_REPORT_TASK:-$REPO_ROOT/e2e-aem-htl-smoke/target/sonar/report-task.txt}"

if [[ -z "$SMOKE_ISSUES_TOKEN" ]]; then
  echo "smoke-aem-htl-plugin.sh: set SONAR_TOKEN or SMOKE_ISSUES_TOKEN" >&2
  exit 2
fi

# SonarQube does not always bind sonar.qualityprofile.* on first import; register the project and
# attach built-in profiles so AEM-JAVA / AEM-HTL rules run on the first scan.
_smoke_bind_profiles() {
  local key="$SMOKE_ISSUES_PROJECT_KEY"
  local base="${SMOKE_ISSUES_HOST%/}"
  local auth=(-u "${SMOKE_ISSUES_TOKEN}:")
  curl -sf "${auth[@]}" -X POST "${base}/api/projects/create?project=${key}&name=${key}" >/dev/null 2>&1 || true
  local code
  code=$(curl -s -o /dev/null -w "%{http_code}" "${auth[@]}" -X POST \
    "${base}/api/qualityprofiles/add_project?language=java&project=${key}&qualityProfile=AEM%20Java")
  if [[ "$code" != "204" ]]; then
    echo "smoke-aem-htl-plugin.sh: qualityprofiles/add_project (java) failed HTTP ${code}" >&2
    return 1
  fi
  code=$(curl -s -o /dev/null -w "%{http_code}" "${auth[@]}" -X POST \
    "${base}/api/qualityprofiles/add_project?language=htl&project=${key}&qualityProfile=HTL")
  if [[ "$code" != "204" ]]; then
    echo "smoke-aem-htl-plugin.sh: qualityprofiles/add_project (htl) failed HTTP ${code}" >&2
    return 1
  fi
}
_smoke_bind_profiles

cd "$REPO_ROOT/e2e-aem-htl-smoke"
# Match README: avoid web/HTML plugin colliding with HTL on .html; reinforce built-in profiles.
mvn -q compile sonar:sonar \
  -Dsonar.host.url="$SMOKE_ISSUES_HOST" \
  -Dsonar.projectKey="$SMOKE_ISSUES_PROJECT_KEY" \
  -Dsonar.token="$SMOKE_ISSUES_TOKEN" \
  -Dsonar.qualityprofile.htl=HTL \
  -Dsonar.qualityprofile.java="AEM Java" \
  -Dsonar.html.file.suffixes=.notexistingsuffix

exec python3 "$REPO_ROOT/scripts/verify-aem-htl-smoke-issues.py"
