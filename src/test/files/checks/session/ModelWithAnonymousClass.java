package com.example;

import com.cognifide.slice.api.model.InitializableModel;
import com.cognifide.slice.mapper.annotation.SliceResource;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

@SliceResource
public class ModelWithAnonymousClass implements InitializableModel {

	@Inject
	private ResourceResolver resourceResolver;

	private List<String> images;

	private List<String> otherImages;

	public ModelWithAnonymousClass() {
		images = FluentIterable.from(Arrays.asList("/image/1"))
			.transform(new Function<String, String>() {
				@Override
				public String apply(String path) {
					return resourceResolver.getResource(path).getValueMap().get("property", String.class);
				}
			}).filter(Predicates.notNull())
			.toList();
	}
	
	@Override
	public void afterCreated() {
		otherImages = gatherImages();
	}

	public List<String> getImages() {
		return images;
	}

	public List<String> getOhterImages() {
		return otherImages;
	}

	private List<String> gatherImages() {
		return createFromPaths(ImmutableList.<String>of("/image/1", "/image/2", "/image/3"));
	}

	private List<String> createFromPaths(List<String> paths) {
		return FluentIterable.from(paths)
			.transform(new Function<String, String>() {
				@Override
				public String apply(String path) {
					return resourceResolver.getResource(path).getValueMap().get("property", String.class);
				}
			}).filter(Predicates.notNull())
			.toList();
	}

	public List<String> transform(List<String> paths, final String property) {
		return FluentIterable.from(paths)
			.transform(new Function<String, String>() {
				@Override
				public String apply(String path) {
					Resource resource = resourceResolver.getResource(path); // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().}}
					return resource.getValueMap().get(property, String.class);
				}
			}).filter(Predicates.notNull())
			.toList();
	}
}
