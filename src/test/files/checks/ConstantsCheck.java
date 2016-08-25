package com.example;

class Constants {
	public static final String UNIQUE_CONSTANT = "limited in occurrence to a given class, situation, or area:";
	public static final String JCR_CREATED = "jcr:created"; // Noncompliant {{Use predefined constant instead of hardcoded value. (interface com.day.cq.commons.jcr.JcrConstants.JCR_CREATED or interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition.PN_CREATED)}}
	public static final String NT_FOLDER = "nt:folder"; // Noncompliant {{Use predefined constant instead of hardcoded value. (interface com.day.cq.commons.jcr.JcrConstants.NT_FOLDER)}}
	public static final String NT_DAM_ASSET;
	static {
		NT_DAM_ASSET = "dam:Asset"; // Noncompliant {{Use predefined constant instead of hardcoded value. (interface com.day.cq.dam.api.DamConstants.NT_DAM_ASSET)}}
	}

	public static String getConstant() {
		return "nt:resource"; // Noncompliant {{Use predefined constant instead of hardcoded value. (interface com.day.cq.commons.jcr.JcrConstants.NT_RESOURCE)}}
	}
}