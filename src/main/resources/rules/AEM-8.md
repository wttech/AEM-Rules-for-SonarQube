Prefer cleaner ``@SlingServlet`` annotation over ``@Properties`` approach. Do not mix up both approaches.
== Noncompliant Code Example
``
@Component
@Service(value = javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.resourceTypes", value = { "some/resource/path" }),
        @Property(name = "sling.servlet.selectors", value = { "selector" }),
        @Property(name = "sling.servlet.extensions", value = { "tab" }),
        @Property(name = "sling.servlet.methods", value = { HttpConstants.METHOD_GET })
})
``
== Compliant Solution
``
@SlingServlet(
        resourceTypes = "some/resource/path",
        selectors = "selector",
        extensions = "tab",
        methods = HttpConstants.METHOD_GET
)
``