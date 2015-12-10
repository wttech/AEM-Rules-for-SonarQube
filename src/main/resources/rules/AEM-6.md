Resource Resolver has a life cycle which begins with the creation of the Resource Resolver using any of the factory methods and ends with calling the ``close()`` method. It is very important to call the ``close()`` method once the resource resolver is not used any more to ensure any system resources are properly clean up.
== Noncompliant Code Example
``
try {
    ResourceResolver resolver = resourceResolverFactory.getResourceResolver(authenticationInfoMap); // Noncompliant; resourceResolver should be closed in finally block
    // ...
} catch (Exception e) {
    // ...
}
``
``
try {
    ResourceResolver resolver = resourceResolverFactory.getResourceResolver(authenticationInfoMap); // Noncompliant; resourceResolver should be closed in finally block
    resolver.close();
} catch (Exception e) {
    // ...
}
``
== Compliant Solution
``
ResourceResolver resolver = null;
try {
    resolver = resourceResolverFactory.getResourceResolver(authenticationInfoMap);
    // ...
} catch (LoginException e) {
    // ...
} finally {
    if (resolver != null) {
        resolver.close();
    }
}
``
== See
[ResourceResolver Javadoc](https://sling.apache.org/apidocs/sling6/org/apache/sling/api/resource/ResourceResolver.html)
