package com.example;

import java.lang.Object;
import java.lang.String;
import java.util.Map;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;

public class ModifiableValueMapUsageCheck {

	public Resource setProperty(Resource resource) {
		ModifiableValueMap resourceProperties = resource.adaptTo(ModifiableValueMap.class);
		if (resourceProperties != null) {
			resourceProperties.put("test", resource.getName());
		}
		return resource;
	}

	public Resource removeProperty(Resource resource) {
		ModifiableValueMap resourcePropertiesToSet = resource.adaptTo(ModifiableValueMap.class);
		removePropertyFromMVM("testName", "testValue",resourcePropertiesToSet);
		return resource;
	}

	public Object getPropertyFromResource(Resource resource) {
		ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class); // Noncompliant ValueMap should be used
		return createdResourceProperties.get("propName");
	}

	public Object getProperty(Resource resource) {
		ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class); // Noncompliant ValueMap should be used
		return getPropertyFromValueMap("propName", createdResourceProperties);
	}

	private void removePropertyFromMVM(String propName, String propValue,Map<String, Object> mvm){
		propName += "other";
		removeOtherPropertyFromMVM(mvm, propName, propValue);
	}

	private void removeOtherPropertyFromMVM(Map<String, Object> mvm2, String propName, String propValue) {
		mvm2.remove(propName, propValue);
	}

	private Object getPropertyFromValueMap(String propName, Map <String,Object> mvm){
		mvm.getOrDefault(propName, "default");
	}

}