Manually created Session should be logged out.
== Example

=== Do

``
Session session = null;
try {
	session = repo.loginService(null, null);
	// do stuff
} catch (RepositoryException e) {
	LOGGER.error("Unable to create session", e);
} finally {
	if (session != null && session.isLive()) {
		session.logout();
	}
}
``

=== Don't

``
Session session = null;
try {
	session = repo.loginService(null, null);
	// do stuff
} catch (RepositoryException e) {
	LOGGER.error("Unable to create session", e);
}
``