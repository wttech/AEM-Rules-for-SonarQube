Prefer cleaner @SlingServlet annotation over @Properties approach. Don't mix up both approaches.

== Example

=== Prefer

``
@SlingServlet(
	resourceTypes = "sling/servlet/default",
	selectors = "selector",
	extensions = "tab",
	methods = HttpConstants.METHOD_GET
)
``

=== Over

``
@Component
@Service(value = javax.servlet.Servlet.class)
@Properties({
			@Property(name = "sling.servlet.resourceTypes", value = { "sling/servlet/default" }),
			@Property(name = "sling.servlet.selectors", value = { "selector" }),
			@Property(name = "sling.servlet.extensions", value = { "tab" }),
			@Property(name = "sling.servlet.methods", value = { HttpConstants.METHOD_GET })
})
``