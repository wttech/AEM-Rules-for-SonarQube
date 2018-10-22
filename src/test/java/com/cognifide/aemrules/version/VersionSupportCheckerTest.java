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

import static com.cognifide.aemrules.version.VersionSupportChecker.DEFAULT_AEM_VERSION;
import static com.cognifide.aemrules.version.VersionSupportChecker.create;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;

public class VersionSupportCheckerTest {

    @Test
    public void shouldSupportAllVersionsWhenAllSet() {
        VersionSupportChecker versionSupportChecker = create(
            CheckSupportingAllVersions.class.getAnnotation(AemVersion.class));
        Assert.assertThat(versionSupportChecker.supports("6.3"), is(true));
        Assert.assertThat(versionSupportChecker.supports("2.1"), is(true));
        Assert.assertThat(versionSupportChecker.supports("2.0"), is(true));
    }

    @Test
    public void shouldSupportAllVersionsBetweenFromAndTo() {
        VersionSupportChecker versionSupportChecker = create(
            CheckSupportingVersionsFromTo.class.getAnnotation(AemVersion.class));
        Assert.assertThat(versionSupportChecker.supports("6.1"), is(true));
        Assert.assertThat(versionSupportChecker.supports("5.2"), is(true));
        Assert.assertThat(versionSupportChecker.supports("5.1"), is(true));
        Assert.assertThat(versionSupportChecker.supports("6.4"), is(true));
        Assert.assertThat(versionSupportChecker.supports("6.5"), is(false));
        Assert.assertThat(versionSupportChecker.supports("5.0"), is(false));
    }

    @Test
    public void shouldSupportAllVersionsFrom() {
        VersionSupportChecker versionSupportChecker = create(
            CheckSupportingVersionsFrom.class.getAnnotation(AemVersion.class));
        Assert.assertThat(versionSupportChecker.supports("100.1"), is(true));
        Assert.assertThat(versionSupportChecker.supports("10.1"), is(true));
        Assert.assertThat(versionSupportChecker.supports("17.2"), is(true));
        Assert.assertThat(versionSupportChecker.supports("10.0"), is(false));
        Assert.assertThat(versionSupportChecker.supports("5.9"), is(false));
    }

    @Test
    public void shouldSupportAllVersionsTo() {
        VersionSupportChecker supportedVersions = create(
            CheckSupportingVersionsTo.class.getAnnotation(AemVersion.class));
        Assert.assertThat(supportedVersions.supports("1.1"), is(true));
        Assert.assertThat(supportedVersions.supports("10.1"), is(true));
        Assert.assertThat(supportedVersions.supports("0.2"), is(true));
        Assert.assertThat(supportedVersions.supports("8.5"), is(true));
        Assert.assertThat(supportedVersions.supports("10.2"), is(false));
        Assert.assertThat(supportedVersions.supports("20.9"), is(false));
    }

    @Test
    public void shouldSupportAllProvidedVersions() {
        VersionSupportChecker supportedVersions = create(
            CheckSupportingVersionsIncluded.class.getAnnotation(AemVersion.class));
        Assert.assertThat(supportedVersions.supports("6.1"), is(true));
        Assert.assertThat(supportedVersions.supports("2.1"), is(true));
        Assert.assertThat(supportedVersions.supports("4.8"), is(true));
        Assert.assertThat(supportedVersions.supports("6.0"), is(false));
        Assert.assertThat(supportedVersions.supports("2.2"), is(false));
    }

    @Test
    public void shouldSupportAllVersionsExcludingProvided() {
        VersionSupportChecker supportedVersions = create(
            CheckSupportingVersionsExcluded.class.getAnnotation(AemVersion.class));
        Assert.assertThat(supportedVersions.supports("6.1"), is(false));
        Assert.assertThat(supportedVersions.supports("2.1"), is(false));
        Assert.assertThat(supportedVersions.supports("4.8"), is(false));
        Assert.assertThat(supportedVersions.supports("6.0"), is(true));
        Assert.assertThat(supportedVersions.supports("2.2"), is(true));
    }

    @Test
    public void shouldUseDefaultVersionWhenProvidedVersionIsEmptyOrNull() {
        VersionSupportChecker supportedVersions = create(
            CheckSupportingDefaultVersion.class.getAnnotation(AemVersion.class));
        Assert.assertThat(supportedVersions.supports(DEFAULT_AEM_VERSION), is(true));
        Assert.assertThat(supportedVersions.supports(""), is(true));
        Assert.assertThat(supportedVersions.supports(null), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenVersionToSupportIsIncorrect() {
        VersionSupportChecker supportedVersions = create(
            CheckSupportingVersionsExcluded.class.getAnnotation(AemVersion.class));
        supportedVersions.supports("f.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenVersionIncorrectProvided1() {
        create(CheckWithIncorrectVersion1.class.getAnnotation(AemVersion.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenVersionIncorrectProvided2() {
        create(CheckWithIncorrectVersion2.class.getAnnotation(AemVersion.class));
    }


    @AemVersion(
        all = true
    )
    private class CheckSupportingAllVersions {

    }

    @AemVersion(
        from = "5.1",
        to = "6.4"
    )
    private class CheckSupportingVersionsFromTo {

    }

    @AemVersion(
        from = "10.1"
    )
    private class CheckSupportingVersionsFrom {

    }

    @AemVersion(
        to = "10.1"
    )
    private class CheckSupportingVersionsTo {

    }

    @AemVersion(
        included = {"6.1", "2.1", "4.8"}
    )
    private class CheckSupportingVersionsIncluded {

    }

    @AemVersion(
        all = true,
        excluded = {"6.1", "2.1", "4.8"}
    )
    private class CheckSupportingVersionsExcluded {

    }

    @AemVersion(
        from = "5.6.7"
    )
    private class CheckWithIncorrectVersion1 {

    }

    @AemVersion(
        included = "a.b"
    )
    private class CheckWithIncorrectVersion2 {

    }

    @AemVersion(
        included = {DEFAULT_AEM_VERSION}
    )
    private class CheckSupportingDefaultVersion {

    }

}