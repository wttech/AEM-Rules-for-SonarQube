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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class Version {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<major>[0-9]+)\\.(?<minor>[0-9])");

    private final int major;

    private final int minor;

    private Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public static Version of(String version, String defaultVersion) {
        return of(StringUtils.defaultIfEmpty(version, defaultVersion));
    }

    public static Version of(String version) {
        Matcher versionMatcher = VERSION_PATTERN.matcher(version);
        if (!versionMatcher.matches()) {
            throw new IllegalArgumentException(
                "Incorrect AEM version format provided: " + version + " expected pattern: [0-9]+.[0-9]");
        }
        int major = Integer.parseInt(versionMatcher.group("major"));
        int minor = Integer.parseInt(versionMatcher.group("minor"));

        return new Version(major, minor);
    }

    public boolean isEqualOrGreaterThan(Version version) {
        return version.major < this.major
            || (version.major == this.major && version.minor <= this.minor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Version version = (Version) o;
        return major == version.major &&
            minor == version.minor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor);
    }

}
