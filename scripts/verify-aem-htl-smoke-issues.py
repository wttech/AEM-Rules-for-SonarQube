#!/usr/bin/env python3
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

"""
After sonar:sonar on e2e-aem-htl-smoke: wait for CE (report-task.txt), then require every
expected AEM-HTL and AEM-JAVA rule (keys hardcoded below; keep in sync with HtlRulesList /
JavaRulesList.getJavaChecks) in GET /api/issues/search (resolved=false, inNewCodePeriod=false).

Invoked from scripts/smoke-aem-htl-plugin.sh — smoke API gate.

Env:
  SMOKE_ISSUES_HOST              Sonar base URL (required)
  SMOKE_ISSUES_TOKEN             user token (required)
  SMOKE_ISSUES_PROJECT_KEY       default aem-htl-smoke-local
  SMOKE_ISSUES_REPORT_TASK       path to e2e-aem-htl-smoke/target/sonar/report-task.txt (required)
"""
from __future__ import annotations

import base64
import json
import os
import sys
import time
import urllib.error
import urllib.parse
import urllib.request
from pathlib import Path
from typing import NamedTuple, Optional

CE_WAIT_S = 300


class _SmokeEnv(NamedTuple):
    base: str
    token: str
    project: str
    rt_path: Path

# JavaRulesList.getJavaChecks() @Rule keys — update when checks change (numeric suffix order).
_JAVA_RULE_KEYS: tuple[str, ...] = (
    "AEM-1",
    "AEM-2",
    "AEM-3",
    "AEM-6",
    "AEM-7",
    "AEM-8",
    "AEM-11",
    "AEM-15",
    "AEM-16",
    "AEM-17",
    "AEM-18",
    "AEM-19",
    "AEM-20",
)


def _expected_rules() -> set[str]:
    htl = {f"AEM-HTL:HTL-{i}" for i in range(17)}
    java = {f"AEM-JAVA:{k}" for k in _JAVA_RULE_KEYS}
    return htl | java


def _load_report_task(path: Path) -> dict[str, str]:
    out: dict[str, str] = {}
    with path.open(encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if not line or "=" not in line:
                continue
            k, _, v = line.partition("=")
            out[k.strip()] = v.strip()
    return out


def _dashboard_overall(dashboard_url: str) -> str:
    if not dashboard_url or "codeScope=" in dashboard_url:
        return dashboard_url
    sep = "&" if "?" in dashboard_url else "?"
    return f"{dashboard_url}{sep}codeScope=overall"


def _wait_ce(ce_url: str, auth: str) -> None:
    deadline = time.time() + CE_WAIT_S
    last = ""
    while time.time() < deadline:
        req = urllib.request.Request(ce_url)
        req.add_header("Authorization", auth)
        with urllib.request.urlopen(req, timeout=120) as resp:
            data = json.load(resp)
        task = data.get("task") or {}
        st = task.get("status") or ""
        last = st
        if st == "SUCCESS":
            return
        if st in ("FAILED", "CANCELED"):
            print(f"verify-aem-htl-smoke-issues: CE task {st!r} {task!r}", file=sys.stderr)
            raise SystemExit(1)
        time.sleep(1)
    print(f"verify-aem-htl-smoke-issues: CE wait timeout (last={last!r})", file=sys.stderr)
    raise SystemExit(1)


def _main_branch(base: str, auth: str, project: str) -> Optional[str]:
    qs = urllib.parse.urlencode({"project": project})
    url = f"{base}/api/project_branches/list?{qs}"
    req = urllib.request.Request(url)
    req.add_header("Authorization", auth)
    try:
        with urllib.request.urlopen(req, timeout=60) as resp:
            data = json.load(resp)
    except urllib.error.URLError:
        # HTTPError is a subclass of URLError; do not list both (python:S5713).
        return None
    for b in data.get("branches") or []:
        if b.get("isMain"):
            return b.get("name")
    return None


def _fetch_issues(base: str, auth: str, project: str, page: int, branch: Optional[str]) -> dict:
    params: dict[str, str] = {
        "componentKeys": project,
        "resolved": "false",
        "inNewCodePeriod": "false",
        "ps": "500",
        "p": str(page),
    }
    if branch:
        params["branch"] = branch
    qs = urllib.parse.urlencode(params)
    url = f"{base}/api/issues/search?{qs}"
    req = urllib.request.Request(url)
    req.add_header("Authorization", auth)
    with urllib.request.urlopen(req, timeout=120) as resp:
        return json.load(resp)


def _load_env() -> Optional[_SmokeEnv]:
    base = os.environ.get("SMOKE_ISSUES_HOST", "").rstrip("/")
    token = os.environ.get("SMOKE_ISSUES_TOKEN", "")
    project = os.environ.get("SMOKE_ISSUES_PROJECT_KEY", "aem-htl-smoke-local")
    rt_path = Path(os.environ.get("SMOKE_ISSUES_REPORT_TASK", "")).resolve()
    if not base or not token:
        print("verify-aem-htl-smoke-issues: set SMOKE_ISSUES_HOST and SMOKE_ISSUES_TOKEN", file=sys.stderr)
        return None
    if not rt_path.is_file():
        print(f"verify-aem-htl-smoke-issues: missing report-task {rt_path}", file=sys.stderr)
        return None
    return _SmokeEnv(base=base, token=token, project=project, rt_path=rt_path)


def _validate_report_task(rt: dict[str, str], project: str) -> Optional[int]:
    if rt.get("projectKey") and rt["projectKey"] != project:
        print(
            f"verify-aem-htl-smoke-issues: projectKey mismatch report-task={rt['projectKey']!r} env={project!r}",
            file=sys.stderr,
        )
        return 2
    if not rt.get("ceTaskUrl"):
        print("verify-aem-htl-smoke-issues: report-task missing ceTaskUrl", file=sys.stderr)
        return 2
    return None


def _auth_header(token: str) -> str:
    return "Basic " + base64.b64encode(f"{token}:".encode()).decode()


def _print_dashboard_line(dashboard_url: str) -> None:
    dash = _dashboard_overall(dashboard_url)
    if dash:
        sys.stdout.write(f"verify-aem-htl-smoke-issues: dashboard (overall): {dash}\n")
        sys.stdout.flush()


def _gather_issue_rule_keys(
    base: str, auth: str, project: str, branch: Optional[str]
) -> tuple[Optional[set[str]], int]:
    found: set[str] = set()
    page = 1
    while True:
        try:
            data = _fetch_issues(base, auth, project, page, branch)
        except urllib.error.HTTPError as e:
            body = e.read()[:800] if e.fp else b""
            print(
                f"verify-aem-htl-smoke-issues: issues/search HTTP {e.code} {body!r}",
                file=sys.stderr,
            )
            return None, 1
        paging = data.get("paging") or {}
        total = int(paging.get("total", data.get("total", 0)))
        page_size = int(paging.get("pageSize", paging.get("ps", 500)))
        for issue in data.get("issues") or []:
            rule = issue.get("rule")
            if rule:
                found.add(rule)
        if page * page_size >= total:
            return found, 0
        page += 1


def _report_rule_coverage(expected: set[str], found: set[str], project: str) -> int:
    missing = sorted(expected - found)
    if missing:
        print(
            "verify-aem-htl-smoke-issues: missing open issues for rule(s):",
            ", ".join(missing),
            file=sys.stderr,
        )
        print(f"  distinct rules seen: {sorted(found)}", file=sys.stderr)
        return 1
    sys.stdout.write(
        f"verify-aem-htl-smoke-issues: OK — all {len(expected)} expected AEM-HTL + AEM-JAVA rules "
        f"have ≥1 open issue on {project}.\n"
    )
    return 0


def main() -> int:
    env = _load_env()
    if env is None:
        return 2

    rt = _load_report_task(env.rt_path)
    err = _validate_report_task(rt, env.project)
    if err is not None:
        return err

    auth = _auth_header(env.token)
    sys.stdout.write("verify-aem-htl-smoke-issues: waiting for CE task...\n")
    sys.stdout.flush()
    _wait_ce(rt["ceTaskUrl"], auth)
    _print_dashboard_line(rt.get("dashboardUrl", ""))

    branch = _main_branch(env.base, auth, env.project)
    found, code = _gather_issue_rule_keys(env.base, auth, env.project, branch)
    if code != 0 or found is None:
        return code

    return _report_rule_coverage(_expected_rules(), found, env.project)


if __name__ == "__main__":
    sys.exit(main())
