package com.example;

import com.google.common.collect.Maps;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

@SlingServlet(paths = "somePath", methods = { HttpConstants.METHOD_GET })
public class SampleServlet extends SlingAllMethodsServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleServlet.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		ResourceResolver resourceResolver = null; // Noncompliant {{ResourceResolver should be closed in finally block.}}
		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);
		} catch (LoginException e) {
			LOGGER.error("Error during getting instance of ResourceResolver class", e);
		} finally {
			if (resourceResolver != null) {
				//resourceResolver.close();
			}
		}
	}

	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		ResourceResolver resourceResolver = null;
		resourceResolver = request.getResourceResolver();
		// do sth
	}

	/**
	 * Create a new session for specified user (impersonating).
	 */
	public static ResourceResolver getResourceResolverForUser(ResourceResolverFactory factory, String userId)
			throws LoginException {
		ResourceResolver resolver;
		if (userId != null) {
			Map<String, Object> authenticationInfo = Maps.newHashMap();
			authenticationInfo.put(ResourceResolverFactory.USER_IMPERSONATION, userId);
			resolver = factory.getServiceResourceResolver(authenticationInfo);
		} else {
			resolver = factory.getServiceResourceResolver(null);
		}

		return resolver;
	}

	public void checkCorrectJumpMethod() {
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = getResourceResolverForUser(resourceResolverFactory, null);
		} catch (LoginException e) {
			e.printStackTrace();
		} finally {
			if (resourceResolver != null) {
				resourceResolver.close();
			}
		}
	}

	public void checkWrongJumpMethod() {
		ResourceResolver resourceResolver = null; // Noncompliant {{ResourceResolver should be closed in finally block.}}
		try {
			resourceResolver = getResourceResolverForUser(resourceResolverFactory, null);
		} catch (LoginException e) {
			e.printStackTrace();
		} finally {
			if (resourceResolver != null) {
				//resourceResolver.close();
			}
		}
	}

}
