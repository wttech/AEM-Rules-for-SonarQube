package com.example;

import java.io.IOException;
import java.lang.Deprecated;

import javax.servlet.ServletException;
import com.day.cq.wcm.api.PageManager;
import javax.jcr.Session;

import com.google.common.base.Objects;
import org.apache.commons.lang.BooleanUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

@Component
@Service(value = javax.servlet.Servlet.class)
public class ThreadSafeFieldCheckExample extends SlingSafeMethodsServlet {

    @Reference
    private LayoutCache layoutCache;

    private ResourceResolver resourceResolver;

    public ResourceResolver resourceResolver2;

    @Deprecated
    public static final ResourceResolver staticOne;

    private PageManager pageManager;

    private Session session;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        // nothing here
    }

}
