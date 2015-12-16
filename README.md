![Cognifide logo](http://cognifide.github.io/Carty/assets/media/cognifide_logo.png)

# About AEM Rules for SonarQube

![AEM Rules for SonarQube](https://raw.githubusercontent.com/Cognifide/AEM-Rules-for-SonarQube/master/assets/logo.png)

## Purpose

As we all know, SonarQube is a great tool that helps us increase quality of our codebase. However, it does apply mainly to general Java issues. As we know, we can hurt ourselves much more doing AEM. This tool is intended to find common bugs and bad smells specific for AEM development. Documentation of each rule is available from SonarQube interface after plugin installation.

## Prerequisites

* SonarQube 4.5.4 or newer
* Java 3.8 plugin
* Maven 2.x, 3.x

## Installation

1. Download `aemrules.jar` or build **AEM Rules for SonarQube** plugin.
2. Paste it into `sonarqube/extensions/plugins` directory.
3. Restart SonarQube.
4. Go to rules section and activate AEM rules in your profile.
 
# Rule set

Below you will find descriptions of all rules available in **AEM Rules for SonarQube** plugin.

- **AEM-1** Use predefined constant in annotation instead of hardcoded value.
  - Use constants available in AEM instead of repeating inline literals.

- **AEM-2** Use predefined constant instead of hardcoded value.
  - Use constants available in AEM instead of repeating inline literals.

- **AEM-3** Non-thread safe object used as a field of Servlet / Filter etc.
  - It is not safe to keep session based object as a field in `Servlet` or `Filter`. Rule checks for the occurrence of any instance or static fields of following types:
    - `org.apache.sling.api.resource.ResourceResolver`
    - `javax.jcr.Session`
    - `com.day.cq.wcm.api.PageManager`

- **AEM-4** Injector should be closed in finally block or created as a resource within try block.
  - Injectors (`com.cognifide.slice.api.injector.InjectorWithContext`) are created in the context of either request or resource resolver. To restore its initial state after using injector, it should be closed in finally block or created as a resource within try block.

- **AEM-5** Injector can be closed using try-with-resources Java 7 feature.
  - Take advantage of Java 7 try-with-resources feature to close `com.cognifide.slice.api.injector.InjectorWithContext`.

- **AEM-6** ResourceResolver should be closed in finally block.
  - According to its [Javadoc](https://sling.apache.org/apidocs/sling6/org/apache/sling/api/resource/ResourceResolver.html), Resource Resolver has a life cycle which begins with the creation of the Resource Resolver using any of the factory methods and ends with calling the `close` method. It is very important to call the `close` method once the resource resolver is not used any more to ensure any system resources are properly clean up.

- **AEM-7** Session should be logged out in finally block.
  - Manually created `javax.jcr.Session` should be logged out after it is no longer needed. The `logout` method releases all resources associated with `Session`.

- **AEM-8** Prefer cleaner `@SlingServlet` annotation.
  - Prefer cleaner `@SlingServlet` annotation over `@Properties` approach. Do not mix up both approaches.

# Release notes

## 0.5

RND-398 Support for Java 3.8 plugin.

# Commercial Support

Technical support can be made available if needed. Please [contact us](mailto:labs-support@cognifide.com) for more details.

We can:

* prioritize your feature request,
* tailor the product to your needs,
* provide a training for your engineers,
* support your development teams.