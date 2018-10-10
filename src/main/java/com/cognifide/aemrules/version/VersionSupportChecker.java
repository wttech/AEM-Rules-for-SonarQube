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
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

public class VersionSupportChecker {

  public static final String VERSION_PROPERTY = "sonarRunner.aemVersion";

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
    boolean result = true;
    if (version != null) {
      Version aemVersion = Version.of(version);
      result = !isExcluded(aemVersion) && supportsVersion(aemVersion);
    }
    return result;
  }

  private boolean isExcluded(Version aemVersion) {
    return excluded.contains(aemVersion);
  }

  private boolean supportsVersion(Version aemVersion){
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
    return version.major > from.major ||
        (version.major == from.major && version.minor >= from.minor);
  }

  private boolean passesTo(Version version) {
    return version.major < to.major ||
        (version.major == to.major && version.minor <= to.minor);
  }

  private static class Version {

    private static final Pattern VERSION_PATTERN = Pattern
        .compile("(?<major>[0-9]+)\\.(?<minor>[0-9])");

    private final int major;

    private final int minor;

    private Version(int major, int minor) {
      this.major = major;
      this.minor = minor;
    }

    public static Version of(String version) {
      Matcher versionMatcher = VERSION_PATTERN.matcher(version);
      if (!versionMatcher.matches()) {
        throw new IllegalArgumentException(
            "Incorrect version format: " + version + " expected: [0-9]+.[0-9]");
      }
      int major = Integer.parseInt(versionMatcher.group("major"));
      int minor = Integer.parseInt(versionMatcher.group("minor"));

      return new Version(major, minor);
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

}
