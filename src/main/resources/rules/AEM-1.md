Use constants available in AEM instead of repeating inline literals.
== Noncompliant Code Example
``
public static final String JCR_CONTENT = "jcr:content";
// (...)
Resource resource = resourceResolver.getResource(resourcePath + JCR_CONTENT);
``
== Compliant Code Example
``
import com.day.cq.commons.jcr.JcrConstants;
// (...)
Resource resource = resourceResolver.getResource(resourcePath + JcrConstants.JCR_CONTENT);
``
