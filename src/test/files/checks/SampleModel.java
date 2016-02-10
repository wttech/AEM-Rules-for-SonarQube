package com.example;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;

/**
 * @author Krzysztof Watral
 */
@SliceResource
public class SampleModel implements InitializableModel {

	private final ResourceResolver resolver;

	private ResourceResolver resolver2;

	@JcrProperty
	private String prop;

	public SampleModel(ResourceResolver resolver) {
		this.resolver = resolver;
	}

	public String getProp() {
		return prop;
	}

	public String getLowerCaseProp() {
		return prop.toLowerCase();
	}

	public String getPropRR() {
		resolver.getAttribute("TEST"); // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object. (except: constructor, com.cognifide.slice.api.model.InitializableModel.afterCreated())}}
		StringUtils.isBlank((String) resolver2.getAttribute("TEST")); // Noncompliant
		return "null";
	}

	public ResourceResolver getResolver() {
		return resolver; // Noncompliant
	}

	public String getNothing() {
		return null;
	}

	@Override
	public void afterCreated() {
		resolver.getAttribute("TEST");
	}
}