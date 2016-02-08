package com.example;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.mapper.annotation.SliceResource;
import org.apache.sling.api.resource.ResourceResolver;

@SliceResource
public class ModelWithSessionLeak implements InitializableModel {

	private final ResourceResolver resolver;

	private Object attribute;

	public ModelWithSessionLeak(ResourceResolver resolver) {
		this.resolver = resolver;
		updateAttribute(resolver);
	}

	public ResourceResolver getResolver() {
		return resolver; // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().}}
	}

	public Object getAttribute() {
		updateAttribute3(resolver);
		update(resolver);
		return attribute;
	}

	@Override
	public void afterCreated() {
		attribute = resolver.getAttribute("attribute");
		updateAttribute3(null);
	}
	
	private void update(ResourceResolver resolver) {
		if (true) {
			updateAttribute2(resolver);
		}
	}

	private void updateAttribute3(ResourceResolver resolver) {
		attribute = resolver.getAttribute("attribute"); // Noncompliant
		attribute = this.resolver.getAttribute("attribute"); // Noncompliant
	}

	private void updateAttribute2(ResourceResolver resolver) {
		attribute = resolver.getAttribute("attribute"); // Noncompliant
		attribute = this.resolver.getAttribute("attribute"); // Noncompliant
	}

	private void updateAttribute(ResourceResolver resolver) {
		attribute = resolver.getAttribute("attribute");
		attribute = this.resolver.getAttribute("attribute");
		updateAttribute2(resolver);
	}

}
