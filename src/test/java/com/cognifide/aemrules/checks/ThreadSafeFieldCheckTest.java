package com.cognifide.aemrules.checks;

import org.junit.Test;

public class ThreadSafeFieldCheckTest extends AbstractBaseTest {

	@Test
	public void checkThreadSafeFieldsInServlet() {
		check = new ThreadSafeFieldCheck();
		filename = "src/test/files/checks/ThreadSafeFieldCheckServlet.java";
		verify();
	}

	@Test
	public void checkThreadSafeFieldsInFilter() {
		check = new ThreadSafeFieldCheck();
		filename = "src/test/files/checks/ThreadSafeFieldCheckFilter.java";
		verify();
	}
}
