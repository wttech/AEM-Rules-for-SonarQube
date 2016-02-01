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
        methods = { "POST" } // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. (class org.apache.sling.api.servlets.HttpConstants.METHOD_POST)}}
)
@Properties({
        @Property(name = "service.vendor", value = ZenGardenConstants.VENDOR_NAME), // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. (interface org.osgi.framework.Constants.SERVICE_VENDOR)}}
        @Property(name = "service.description", value = "Provides import process.") // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. (interface org.osgi.framework.Constants.SERVICE_DESCRIPTION)}}
})
public class AnnotationsConstantsCheck extends SlingAllMethodsServlet {

    private static final String ID_PARAM = "service.description";

    @Override("Test")
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override("service.description") // Noncompliant {{Use predefined constant in annotation instead of hardcoded value. (interface org.osgi.framework.Constants.SERVICE_DESCRIPTION)}}
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }

}
