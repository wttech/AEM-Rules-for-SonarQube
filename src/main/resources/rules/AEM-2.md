Use constants available in AEM instead of repeating inline literals.
== Noncompliant Code Example
``
@SlingServlet(resourceTypes = "sling/servlet/default", selectors = { "components" }, extensions = "json", methods = "GET")
public class ComponentServlet extends SlingAllMethodsServlet {
``
== Compliant Code Example
``
@SlingServlet(resourceTypes = ServletResolverConstants.DEFAULT_SERVLET_NAME, selectors = { "components" }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class ComponentServlet extends SlingAllMethodsServlet {
``
