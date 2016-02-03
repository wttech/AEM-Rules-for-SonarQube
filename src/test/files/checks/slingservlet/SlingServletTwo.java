package com.example;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;

@Service // Noncompliant {{@Component nor @Service annotation is not needed when @SlingServlet is used.}}
@SlingServlet(resourceTypes = "sling/servlet/default", selectors = "someSelector", extensions = "json", methods = "GET")
@Properties({
		@Property(name = Constants.SERVICE_VENDOR, value = "Cognifide"),
		@Property(name = Constants.SERVICE_DESCRIPTION, value = "Some description") })
public class SlingTestTwo extends SlingSafeMethodsServlet {

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		//do sth
	}
}
