package com.example;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.mapper.annotation.SliceResource;
import org.apache.sling.api.resource.ResourceResolver;

@SliceResource
public class SampleModel2 implements InitializableModel {

	private final ResourceResolver resolver;

	private Object attribute;

	public SampleModel2(ResourceResolver resolver) {
		this.resolver = resolver;
		updateAttribute(resolver);
	}

	public ResourceResolver getResolver() {
		return resolver; // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object. (except: constructor, com.cognifide.slice.api.model.InitializableModel.afterCreated())}}
	}

	public Object getAttribute() {
		updateAttribute3(resolver); // Noncompliant
		update(resolver); // Noncompliant
		return attribute;
	}

	@Override
	public void afterCreated() {
		attribute = resolver.getAttribute("attribute");
		updateAttribute3(null);
	}
	
	private void update(ResourceResolver resolver) {
		updateAttribute2(resolver);
	}

	private void updateAttribute3(ResourceResolver resolver) {
		attribute = resolver.getAttribute("attribute");
		attribute = this.resolver.getAttribute("attribute");
	}

	private void updateAttribute2(ResourceResolver resolver) {
		attribute = resolver.getAttribute("attribute");
		attribute = this.resolver.getAttribute("attribute");
	}

	private void updateAttribute(ResourceResolver resolver) {
		attribute = resolver.getAttribute("attribute");
		attribute = this.resolver.getAttribute("attribute");
		updateAttribute2(resolver);
	}

}
