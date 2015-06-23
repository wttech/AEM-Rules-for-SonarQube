package com.example;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.servlets.resolver.internal.ServletResolverConstants;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.sling.servlets.resolver.internal.ServletResolverConstants.SLING_SERVLET_EXTENSIONS;

@SlingServlet(methods = "GET")
@Properties({
		@Property(name = Constants.SERVICE_VENDOR, value = "Cognifide"),
		@Property(name = Constants.SERVICE_DESCRIPTION, value = "Some description"),
		@Property(name = ServletResolverConstants.SLING_SERVLET_SELECTORS, value = "selector"),
		@Property(name = SLING_SERVLET_EXTENSIONS, value = "json"),
		@Property(name = "sling.servlet.resourceTypes", value = { "sling/servlet/default" })
})
public class SlingTestThree extends SlingSafeMethodsServlet {

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		//do sth
	}
}
