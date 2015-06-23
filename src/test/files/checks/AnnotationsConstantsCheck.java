package com.example;

import static com.cognifide.zg.webapp.foundation.helpers.JsonHelper.Type;
import static com.cognifide.zg.webapp.foundation.helpers.JsonHelper.writeJson;
import static com.cognifide.zg.webapp.foundation.helpers.JsonHelper.writeMessage;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognifide.slice.api.injector.InjectorWithContext;
import com.cognifide.slice.util.InjectorUtil;

@SlingServlet(resourceTypes = { "some/resource/path" }, selectors = { "inspect", "install" }, extensions = { "json" }, methods = { "POST" })
@Properties({ @Property(name = "service.vendor", value = ZenGardenConstants.VENDOR_NAME),
        @Property(name = "service.description", value = "Provides import process.") })
public class AnnotationsConstantsCheck extends SlingAllMethodsServlet {

    private static final String ID_PARAM = "service.description";

    @Override("Test")
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override("service.description")
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }

}
