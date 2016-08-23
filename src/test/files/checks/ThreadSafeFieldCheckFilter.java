package com.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.tagging.TagManager;

@SlingFilter(scope = SlingFilterScope.COMPONENT, order = Integer.MAX_VALUE, metatype = true, label =
        "label", description = "description")
public class ThreadSafeFieldCheckFilterExample implements Filter {

    private PageManager pageManager; // Noncompliant {{Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.}}

    private TagManager tagManager; // Noncompliant

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // init
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        // filter
    }

    @Override
    public void destroy() {
        // nothing to do
    }

}
