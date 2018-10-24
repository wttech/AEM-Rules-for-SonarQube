The [`ResourceResolver`](https://sling.apache.org/apidocs/sling10/org/apache/sling/api/resource/ResourceResolver.html)
should be initialised in  [`try-with-resources` block](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
which ensures that it will be automatically closed because of the [`Autocloseable` interface](https://docs.oracle.com/javase/8/docs/api/java/lang/AutoCloseable.html).

Therefore, you should always initialise ResourceResolver in 'try-with-resources' block

== Noncompliant Code Example

```
protected void getResourceResolver() {
    ResourceResolver resourceResolver = null;
    try {
        resourceResolver = resourceResolverFactory.getServiceResourceResolver(null); // Noncompliant
    } catch (LoginException e) {
        System.out.println("something went wrong");
    } finally {
        if (resourceResolver != null) {
            //resourceResolver.close();
        }
    }
}
```

== Compliant Solution

```
private void getResourceResolver() {
    try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(null)) {
        resourceResolver.getResource("path/to/resource");
    } catch (LoginException e) {
        System.out.println("something went wrong");
    }
}
```

