[![Cognifide logo](http://cognifide.github.io/images/cognifide-logo.png)](http://www.cognifide.com/)

[![Build Status](https://travis-ci.org/Cognifide/AEM-Rules-for-SonarQube.svg?branch=master)](https://travis-ci.org/Cognifide/AEM-Rules-for-SonarQube)
[![Coverage Status](https://coveralls.io/repos/github/Cognifide/AEM-Rules-for-SonarQube/badge.svg?branch=master)](https://coveralls.io/github/Cognifide/AEM-Rules-for-SonarQube?branch=master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=AEM-Rules-for-SonarQube&metric=alert_status)](https://sonarcloud.io/dashboard?id=AEM-Rules-for-SonarQube)
# About AEM Rules for SonarQube

![AEM Rules for SonarQube](https://raw.githubusercontent.com/Cognifide/AEM-Rules-for-SonarQube/master/assets/logo.png)

## Purpose

As we all know, SonarQube is a great tool that helps us increase quality of our codebase. However, it does apply mainly to general Java issues. As we know, we can hurt ourselves much more doing AEM. [Adobe Experience Manager](https://docs.adobe.com/docs/en/aem/6-2.html) is a comprehensive content management platform solution for building websites, mobile apps and forms. This tool is intended to find common bugs and bad smells specific for AEM development. Documentation of each rule is available from SonarQube interface after plugin installation.

## Prerequisites

Each release has its own prerequisites section, for more information please check [releases page](https://github.com/Cognifide/AEM-Rules-for-SonarQube/releases).

## Installation

### Update Center

Go to your SonarQube instance administration console and open Update Center. Find AEM Rules for SonarQube plugin and click install!

### Manual

1. [Download](https://github.com/Cognifide/AEM-Rules-for-SonarQube/releases) `aemrules-x.y.jar` or build **AEM Rules for SonarQube** plugin.
2. Paste it into `sonarqube/extensions/plugins` directory.
3. Restart SonarQube.
4. Go to rules section and activate AEM rules in your profile.

## Rule AEM version support

Each rule is configured to be executed by Sonar Runner only for the AEM version which is supporting. By providing AEM version for the parameter `sonarRunner.aemVersion` we are informing Sonar Runner to use only set of rules supporting current AEM version. When the parameter is not provided then a default AEM version is used (currently 6.4)

### Running Sonar Runner

When running Sonar Runner please add property `sonarRunner.aemVersion` with AEM version for which sonar analyzes should be run.

`sonarRunner.aemVersion=<MAJOR_VERSION>.<MINOR_VERSION>`

- Example using maven
```
mvn sonar:sonar -DsonarRunner.aemVersion=6.4
```

- Example using [Gradle AEM Plugin](https://github.com/Cognifide/gradle-aem-plugin)
```
gradlew sonarQube -DsonarRunner.aemVersion=6.4
```

# Rule set

Below you will find descriptions of all rules available in **AEM Rules for SonarQube** plugin.

## Good practices

- **AEM-1** Use predefined constant in annotation instead of hardcoded value.
  - Use constants available in AEM instead of repeating inline literals.

- **AEM-2** Use predefined constant instead of hardcoded value.
  - Use constants available in AEM instead of repeating inline literals.

- **AEM-8** Prefer cleaner `@SlingServlet` annotation.
  - Prefer cleaner `@SlingServlet` annotation over `@Properties` approach. Do not mix up both approaches.

- **AEM-14** Using http literal hardcoded makes it difficult to switch to https later on.
  - We should not use http as a literal in our projects because if we want to switch to https, our code will be not ready.

- **AEM-15** Usage of ``synchronized`` keyword should be avoided if possible.
  - Usage of ``synchronized`` keyword should be avoided if possible. Check if using ``synchronized`` can be replaced with more sophisticated solution.

- **AEM-17** No mutator methods invoked on ``ModifiableValueMap``
  - ``ModifiableValueMap`` should be replaced by ``ValueMap`` if no mutator methods are invoked.

## Possible bugs

- **AEM-3** Non-thread safe object used as a field of Servlet / Filter etc.
  - It is not safe to keep session based object as a field in `Servlet` or `Filter`. Rule checks for the occurrence of any instance or static fields of following types:
    - `org.apache.sling.api.resource.ResourceResolver`
    - `javax.jcr.Session`
    - `com.day.cq.wcm.api.PageManager`
    - `com.day.cq.wcm.api.components.ComponentManager`
    - `com.day.cq.wcm.api.designer.Designer`
    - `com.day.cq.dam.api.AssetManager`
    - `com.day.cq.tagging.TagManager`
    - `com.day.cq.security.UserManager`
    - `org.apache.jackrabbit.api.security.user.Authorizable`
    - `org.apache.jackrabbit.api.security.user.User`
    - `org.apache.jackrabbit.api.security.user.UserManager`

- **AEM-6** ResourceResolver should be closed in finally block.
  - According to its [Javadoc](https://sling.apache.org/apidocs/sling6/org/apache/sling/api/resource/ResourceResolver.html), Resource Resolver has a life cycle which begins with the creation of the Resource Resolver using any of the factory methods and ends with calling the `close` method. It is very important to call the `close` method once the resource resolver is not used any more to ensure any system resources are properly clean up.

- **AEM-7** Session should be logged out in finally block.
  - Manually created `javax.jcr.Session` should be logged out after it is no longer needed. The `logout` method releases all resources associated with `Session`.

- **AEM-11** Do not use deprecated administrative access methods
  - Administrative access to the resource tree and JCR Repository by means of usage of ``ResourceResolverFactory.getAdministrativeResourceResolver`` and ``SlingRepository.loginAdministrative`` has been deprecated. Use ``ResourceResolverFactory.getServiceResourceResolver`` or ``SlingRepository.loginService`` respectively.

## [Slice](https://github.com/Cognifide/Slice) related

- **AEM-4** Injector should be closed in finally block or created as a resource within try block.
  - Injectors (`com.cognifide.slice.api.injector.InjectorWithContext`) are created in the context of either request or resource resolver. To restore its initial state after using injector, it should be closed in finally block or created as a resource within try block.

- **AEM-5** Injector can be closed using try-with-resources Java 7 feature.
  - Take advantage of Java 7 try-with-resources feature to close `com.cognifide.slice.api.injector.InjectorWithContext`.

- **AEM-9** Objects annotated by `@SliceResource` should not use (except: constructor, `com.cognifide.slice.api.model.InitializableModel.afterCreated()`) and return any session based object.
  - Objects annotated by `@SliceResource` should not use any session based objects, except places like constructor and overridden `com.cognifide.slice.api.model.InitializableModel.afterCreated()` method.

- **AEM-10** Use ``ModelProvider#getListFromResources`` instead of iteration
  - Slice provides method for creating list of models from specified resources given as an ``Iterator``. Instead of iterating over resources yourself, use ``ModelProvider#getListFromResources`` method.

- **AEM-12** Fields annotated by `@JcrProperty` shouldn't be accessed from constructor.
  - Fields that are annotated with `@JcrProperty` should not be accessed from within constructor.

## [Sling Models](https://sling.apache.org/documentation/bundles/models.html) related

- **AEM-16** Optional is defined as ``DefaultInjectionStrategy``
  - Usage of ``@Optional`` annotation is redundant, when ``defaultInjectionStrategy`` is ``OPTIONAL``.

# Release notes

## 0.8

* Improved AEM-1 & AEM-2 issues messages to be more verbose.
* Increased unit test branch coverage.
* Plugin adapted to meet requirements to be listed in [SonarQube Update Center](http://docs.sonarqube.org/display/PLUG/Plugin+Library)
* Updated licensing info.

## 0.7

* Support for Java 8
* Support for SonarQube 5.6.1+
* Support for Java plugin 4.0+
* Fixed multiple adaptables bug for AEM-16 rule.
* Added more non-thread-safe classes to AEM-3 rule.

## 0.6

* Support for SQ 5.4+

## 0.5

* Support for Java 3.13.1 plugin
* AEM-6 - fix for recurrsion issue
* Constants reviewed - removed too common literals
* Number of new AEM Rules

# License

Copyright 2015-2016 Cognifide Ltd.

Licensed under the Apache License, Version 2.0

# Commercial Support

Technical support can be made available if needed. Please [contact us](mailto:labs-support@cognifide.com) for more details.

We can:

* prioritize your feature request,
* tailor the product to your needs,
* provide a training for your engineers,
* support your development teams.
