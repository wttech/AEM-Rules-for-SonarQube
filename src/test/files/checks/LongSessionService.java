package com.example;

import java.util.Map;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.servlet.http.HttpServletRequest;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

@Component
public class LongSessionService implements EventListener {

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	private ResourceResolver resolver;

	private ServiceRegistration registration;

	@Activate
	protected void activate(final BundleContext bundleContext, final Map<String, Object> properties) {
		try {
			resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
			registerObservation();
		} catch (LoginException x) {
		}
	}

	private void registerObservation() {
		final Session session = getSession();
		if (session != null) {
			try {
				session.getWorkspace().getObservationManager().addEventListener(this, 63, "/", true, null, null, true);
			} catch (RepositoryException x) {
			}
		}
	}

	private Session getSession() {
		return resolver.adaptTo(Session.class);
	}

	@Deactivate
	protected void deactivate() {
		if (registration != null) {
			registration.unregister();
			registration = null;
		}
		unregisterObservation();
		if (resolver != null) {
			resolver.close();
			resolver = null;
		}
	}

	private void unregisterObservation() {
		final Session session = getSession();
		if (session != null) {
			try {
				session.getWorkspace().getObservationManager().removeEventListener(this);
			} catch (RepositoryException x) {
			}
		}
	}

	@Override
	public void onEvent(EventIterator events) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public String getLoginPage(final HttpServletRequest request) {
		final String loginPage = request.getParameter("loignPage");
		if (loginPage != null) {
			if (ResourceUtil.isNonExistingResource(resolver.resolve(loginPage))) {
				System.out.println("Login page " + loginPage);
			}
		}
		return loginPage;
	}

	public String getPath(final HttpServletRequest request) {
		String path = request.getParameter("resource");
		final Resource mappedResource = resolver.resolve(request, path);
		if (!ResourceUtil.isNonExistingResource(mappedResource)) {
			path = mappedResource.getPath();
		}
		return path;
	}

	public String toRawPath(final String path) {
		String result = "";
		if (resolver != null) {
			final Resource loginPageResource = resolver.resolve(path);
			if (!ResourceUtil.isNonExistingResource(loginPageResource)) {
				result = resolver.map(loginPageResource.getPath());
			}
		}
		return result;
	}

	public boolean enable(final String rootPath) {
		final String fullPath = resolver.map(rootPath);
		return null != fullPath;
	}
}
