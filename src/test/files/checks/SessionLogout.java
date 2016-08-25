package com.example;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(immediate = true)
public class SessionLogout {

	@Reference
	private SlingRepository repository;

	public void one() {
		Session session = null;
		try {
			session = createAdminSession();
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}

	public void two() {
		Session session = null;
		try {
			session = repository.loginAdministrative(null);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}

	public void three() {
		Session session = null;
		try {
			session = repository.loginService(null, null);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}

	public void four() {
		Session session = null; // Noncompliant {{Session should be logged out in finally block.}}
		session = createAdminSession();
	}

	public void five() {
		Session session = null; // Noncompliant
		try {
			session = repository.loginAdministrative(null);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isLive()) {
				//	session.logout();
			}
		}
	}

	public void six() {
		Session session = null; // Noncompliant
		String plotTwist = "twist";
		plotTwist = "anotherTwist";
		plotTwist = getMeAnotherTwist();
		plotTwist = StringUtils.capitalize(plotTwist);
		try {
			session = repository.loginService(null, null);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isLive()) {
				//	session.logout();
				plotTwist.toString();
			}
		}
	}

	public void seven() {
		Session session = null; // Noncompliant
		session = jump();
	}

	private Session createAdminSession() {
		Session result = null;
		try {
			result = repository.loginAdministrative(null);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Session jump() {
		Session result = null;
		result = createAdminSession();
		return result;
	}

	private Session eight() {
		Session max = createAdminSession();
		Session result = max;
		return result;
	}

	private String getMeAnotherTwist() {
		return "lastOne";
	}

}