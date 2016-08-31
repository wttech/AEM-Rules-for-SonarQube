package com.example;

class Constants {
	public static final String UNIQUE_CONSTANT = "limited in occurrence to a given class, situation, or area:";
	public static final String JCR_CREATED = "jcr:created"; // Noncompliant {{Use predefined constant instead of hardcoded value. Use constant JCR_CREATED from interface com.day.cq.commons.jcr.JcrConstants or constant PN_CREATED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition.}}
	public static final String NT_FOLDER = "nt:folder"; // Noncompliant {{Use predefined constant instead of hardcoded value. Use constant NT_FOLDER from interface com.day.cq.commons.jcr.JcrConstants.}}
	public static final String NT_DAM_ASSET;
	static {
		NT_DAM_ASSET = "dam:Asset"; // Noncompliant {{Use predefined constant instead of hardcoded value. Use constant NT_DAM_ASSET from interface com.day.cq.dam.api.DamConstants.}}
	}

	public static String getConstant() {
		return "nt:resource"; // Noncompliant {{Use predefined constant instead of hardcoded value. Use constant NT_RESOURCE from interface com.day.cq.commons.jcr.JcrConstants.}}
	}
}