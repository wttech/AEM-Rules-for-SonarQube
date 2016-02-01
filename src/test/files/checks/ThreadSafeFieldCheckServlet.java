package com.example;

import javax.jcr.Session;
import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import com.day.cq.wcm.api.PageManager;

@Component
@Service(value = javax.servlet.Servlet.class)
public class ThreadSafeFieldCheckExample extends SlingSafeMethodsServlet {

    @Reference
    private LayoutCache layoutCache;

    private ResourceResolver resourceResolver; // Noncompliant {{Usage of org.apache.sling.api.resource.ResourceResolver as a field is not thread safe.}}

    public ResourceResolver resourceResolver2; // Noncompliant

    @Deprecated
    public static final ResourceResolver staticOne; // Noncompliant

    private PageManager pageManager; // Noncompliant {{Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.}}

    private Session session; // Noncompliant {{Usage of javax.jcr.Session as a field is not thread safe.}}

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        // nothing here
    }

}
