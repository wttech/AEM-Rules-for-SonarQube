
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;

class A {

	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private SlingRepository repository;

	public ResourceResolver getResourceResolver(Map<String, Object> credentials) {
		ResourceResolver resolver = null;
		try {
			resolver = resolverFactory.getAdministrativeResourceResolver(credentials); // Noncompliant {{Method 'getAdministrativeResourceResolver' is deprecated. Use 'getServiceResourceResolver' instead.}}
		} catch (LoginException e) {
			//
		}
		return resolver;
	}

	public Session getSession(String workspace) {
		Session session = null;
		try {
			session = repository.loginAdministrative(workspace); // Noncompliant {{Method 'loginAdministrative' is deprecated. Use 'loginService' instead.}}
		} catch (RepositoryException e) {
			//
		}
		return session;
	}

}
