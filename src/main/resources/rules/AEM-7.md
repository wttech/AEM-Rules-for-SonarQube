Manually created ``Session`` should be logged out after it is no longer needed. The ``logout()`` method releases all resources associated with ``Session``.
== Noncompliant Code Example
``
Session session = null;
try {
	session = repository.loginService("myService", null);
	// ...
} catch (RepositoryException e) {
	// ...
} 
``
== Compliant Solution
``
Session session = null;
try {
	session = repository.loginService("myService", null);
	// ...
} catch (RepositoryException e) {
	// ...
} finally {
    if (session != null && session.isLive()) {
        session.logout();
    }
}
``