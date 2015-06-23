package com.cognifide.aemrules.checks;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestClasspath {

	/**
	 * JAR dependencies for classpath execution
	 */
	public static final List<File> CLASSPATH_JAR;

	/**
	 * When run from maven.
	 */
	public static final String SUREFIRE_TEST_CLASS_PATH = "surefire.test.class.path";

	/**
	 * When run from IDE.
	 */
	public static final String JAVA_CLASS_PATH = "java.class.path";

	static {
		CLASSPATH_JAR = new ArrayList<>();
		String classPath = StringUtils.defaultIfBlank(System.getProperty(SUREFIRE_TEST_CLASS_PATH), System.getProperty(JAVA_CLASS_PATH));
		if (StringUtils.isNotBlank(classPath)) {
			for (String jar : classPath.split(File.pathSeparator)) {
				if (jar.endsWith(".jar")) {
					CLASSPATH_JAR.add(new File(jar));
				}
			}
		}
	}

}
