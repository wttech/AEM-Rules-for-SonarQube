package com.example;

import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

@SlingServlet(
        resourceTypes = { "some/resource/path" },
        selectors = { "inspect", "install" },
        extensions = { "json" },
        methods = { "POST" } // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. Use constant METHOD_POST from class org.apache.sling.api.servlets.HttpConstants.}}
)
@Properties({
        @Property(name = "service.vendor", value = ZenGardenConstants.VENDOR_NAME), // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. Use constant SERVICE_VENDOR from interface org.osgi.framework.Constants.}}
        @Property(name = "service.description", value = "Provides import process.") // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. Use constant SERVICE_DESCRIPTION from interface org.osgi.framework.Constants.}}
})
public class AnnotationsConstantsCheck extends SlingAllMethodsServlet {

    private static final String ID_PARAM = "service.description";

    @Override("Test")
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override("service.description") // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. Use constant SERVICE_DESCRIPTION from interface org.osgi.framework.Constants.}}
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }

}
