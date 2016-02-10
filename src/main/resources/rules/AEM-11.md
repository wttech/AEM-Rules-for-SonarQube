Administrative access to the resource tree and JCR Repository by means of usage of ``ResourceResolverFactory.getAdministrativeResourceResolver`` and ``SlingRepository.loginAdministrative`` has been deprecated.
Use ``ResourceResolverFactory.getServiceResourceResolver`` or ``SlingRepository.loginService`` respectively.
== Noncompliant Code Example
``
ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(credentials);
``
``
Session session = slingRepository.loginAdministrative(workspace);
``
== Compliant Solution
``
try (ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(null)) {
	...
}
``
or
``
Map<String, Object> param = new HashMap<String, Object>();
param.put(ResourceResolverFactory.SUBSERVICE, "sub service name");
try (ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(param)) {
	...
}
``
or
``
Session session = slingRepository.loginService(subServiceName, workspace);
``
== See
[ResourceResolverFactory Javadoc](https://docs.adobe.com/docs/en/aem/6-0/develop/ref/javadoc/org/apache/sling/api/resource/ResourceResolverFactory.html#getAdministrativeResourceResolver%28java.util.Map%29)
[Service Authentication](http://sling.apache.org/documentation/the-sling-engine/service-authentication.html)
