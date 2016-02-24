package com.example;

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;

public class RecursionCheck {

	public void processResource(Resource resource) {
		if (isGreen(resource) || isRed(resource)) {
			Iterator<Resource> iterator = resource.listChildren();
			while (iterator.hasNext()) {
				processResource(iterator.next());
			}
		}
	}

	private boolean isGreen(Resource resource) {
		return StringUtils.equals("green", resource.getValueMap().get("colour", String.class));
	}

	private boolean isRed(Resource resource) {
		return StringUtils.equals("red", resource.getValueMap().get("colour", String.class));
	}
}
