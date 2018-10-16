It is not safe to keep session based object as a field in ``Servlet``, ``Filter``, or ``EventHandler``.
As well as any Declarative Services component. Rule checks for the occurrence of any instance or static fields of following types:
* org.apache.sling.api.resource.ResourceResolver
* javax.jcr.Session
* com.day.cq.wcm.api.PageManager
== Noncompliant Code Example
``
public class ComponentsServlet extends SlingSafeMethodsServlet {

    public static final ResourceResolver staticResolver; // Noncompliant

    private ResourceResolver resolver; // Noncompliant

    public ResourceResolver publicResolver; // Noncompliant

    private PageManager pageManager; // Noncompliant

    private Session session; // Noncompliant
``
``
public class ComponentsFilter implements Filter {

    private PageManager pageManager; // Noncompliant
``
