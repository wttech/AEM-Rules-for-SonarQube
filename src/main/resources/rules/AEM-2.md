Use constants available in AEM instead of repeating inline literals.
== Noncompliant Code Example
``
@SlingServlet(
        resourceTypes = { "some/resource/path" },
        selectors = { "inspect", "install" },
        extensions = { "json" },
        methods = { "POST" } // Noncompliant; Use predefined constant available in org.apache.sling.api.servlets.HttpConstants.METHOD_POST
)
public class ComponentServlet extends SlingAllMethodsServlet {
``
== Compliant Solution
``
import org.apache.sling.api.servlets.HttpConstants;
// ...
@SlingServlet(
        resourceTypes = { "some/resource/path" },
        selectors = { "inspect", "install" },
        extensions = { "json" },
        methods = { HttpConstants.METHOD_POST }
)
public class ComponentServlet extends SlingAllMethodsServlet {
``
