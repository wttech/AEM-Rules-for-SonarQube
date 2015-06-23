package com.example;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "sling.servlet.resourceTypes", value = { "sling/servlet/default" }),
		@Property(name = "sling.servlet.selectors", value = { "selector" }),
		@Property(name = "sling.servlet.extensions", value = { "tab" }),
		@Property(name = "sling.servlet.methods", value = { HttpConstants.METHOD_GET }) })
public class SlingTestOne extends SlingSafeMethodsServlet {

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		//do sth
	}
}
