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
package com.cognifide.aemrules.version;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

public class VersionSupportChecker {

    public static final String VERSION_PROPERTY = "sonarRunner.aemVersion";

    public static final String DEFAULT_AEM_VERSION = "6.4";

    private boolean all = false;

    private Set<Version> included;

    private Set<Version> excluded;

    private boolean fromSet = false;

    private boolean toSet = false;

    private Version from;

    private Version to;

    private VersionSupportChecker(AemVersion aemVersion) {
        from(aemVersion.from());
        to(aemVersion.to());
        included(aemVersion.included());
        excluded(aemVersion.excluded());
        all(aemVersion.all());
    }

    public static VersionSupportChecker create(AemVersion aemVersion) {
        return new VersionSupportChecker(aemVersion);
    }

    private void from(String version) {
        if (StringUtils.isNotEmpty(version)) {
            this.fromSet = true;
            this.from = Version.of(version);
        }
    }

    private void to(String version) {
        if (StringUtils.isNotEmpty(version)) {
            this.toSet = true;
            this.to = Version.of(version);
        }
    }

    private void included(String[] versions) {
        this.included = Arrays.stream(versions)
            .map(Version::of)
            .collect(Collectors.toSet());
    }

    private void excluded(String[] versions) {
        this.excluded = Arrays.stream(versions)
            .map(Version::of)
            .collect(Collectors.toSet());
    }

    private void all(boolean all) {
        this.all = all;
    }

    public boolean supports(String version) {
        Version aemVersion = Version.of(version, DEFAULT_AEM_VERSION);
        return !isExcluded(aemVersion) && supportsVersion(aemVersion);
    }

    private boolean isExcluded(Version aemVersion) {
        return excluded.contains(aemVersion);
    }

    private boolean supportsVersion(Version aemVersion) {
        return all ||
            included.contains(aemVersion) ||
            isBetween(aemVersion) ||
            isFrom(aemVersion) ||
            isTo(aemVersion);
    }

    private boolean isBetween(Version version) {
        return fromSet && toSet && passesFrom(version) && passesTo(version);
    }

    private boolean isFrom(Version version) {
        return fromSet && !toSet && passesFrom(version);
    }

    private boolean isTo(Version version) {
        return toSet && !fromSet && passesTo(version);
    }

    private boolean passesFrom(Version version) {
        return version.isEqualOrGreaterThan(from);
    }

    private boolean passesTo(Version version) {
        return to.isEqualOrGreaterThan(version);
    }

}
