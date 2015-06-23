package com.cognifide.aemrules.extensions;

import junit.framework.Assert;
import org.junit.Test;

public class RulesLoaderTest {

	@Test
	public void testGetDescriptionFromResources() throws Exception {
		Assert.assertEquals(new RulesLoader().getDescriptionFromResources("UnitTest"), "Unit Test.");
	}
}
