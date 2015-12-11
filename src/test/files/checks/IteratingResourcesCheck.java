package com.example;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import com.cognifide.slice.api.provider.ModelProvider;

public class IteratingResourcesCheck {

	public List<SimpleModel> iteratingWhile(Iterator<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		while (resources.hasNext()) { // Noncompliant {{Use ModelProvider#getListFromResources instead of iteration}}
			models.add(modelProvider.get(SimpleModel.class, resources.next()));
		}
		return models;
	}

	public List<SimpleModel> iteratingWhileInnerMethod(Iterator<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		while (resources.hasNext()) { // Noncompliant
			Resource resource = resources.next();
			addModel(models, modelProvider, SimpleModel.class, resource);
		}
		return models;
	}

	public List<SimpleModel> iteratingFor(Iterator<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		for (Iterator<Resource> iterator = resources; iterator.hasNext();) { // Noncompliant
			models.add(modelProvider.get(SimpleModel.class, iterator.next()));
		}
		return models;
	}

	public List<SimpleModel> iteratingForInnerMethod(Iterator<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		for (Iterator<Resource> iterator = resources; iterator.hasNext();) { // Noncompliant
			Resource resource = iterator.next();
			addModel(models, modelProvider, SimpleModel.class, resource);
		}
		return models;
	}

	public List<SimpleModel> iteratingForInnerMethod(Iterator<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		for (; resources.hasNext();) { // Noncompliant
			Resource resource = iterator.next();
			addModel(models, modelProvider, SimpleModel.class, resource);
		}
		return models;
	}


	private void addModel(List<SimpleModel> models, ModelProvider modelProvider, Class<?> modelClass, Resource resource) {
		models.add(modelProvider.get(modelClass, resource));
	}

	public List<SimpleModel> iteratingForEach(Iterable<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		for (Resource resource : resources) { // Noncompliant
			models.add(modelProvider.get(SimpleModel.class, resource));
		}
		return models;
	}

	public List<SimpleModel> iteratingForNotCallingModelProvider(Iterator<Resource> resources, ModelProvider modelProvider) {
		List<SimpleModel> models = new ArrayList<>();
		for (Iterator<Resource> iterator = resources; iterator.hasNext();) {
			//models.add(modelProvider.get(SimpleModel.class, resources.next()));
		}
		return models;
	}

	public List<SimpleModel> getModelsListFromIterator(Iterator<Resource> resources, ModelProvider modelProvider) {
		return modelProvider.getListFromResources(SimpleModel.class, resources);
	}

	public List<SimpleModel> getModelsListFromItarable(Iterable<Resource> resources, ModelProvider modelProvider) {
		return modelProvider.getListFromResources(SimpleModel.class, resources.iterator());
	}

	public void loops(Iterator<String> stringIt, List<String> stringList, Resource resource, ModelProvider modelProvider) {
		for(;;) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		for(int i = 0; i < 1; i++) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		while(true) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		for (String s : Collections.<String>emptyList()) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		for (Iterator<String> it = stringList.iterator(); it.hasNext();) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		for (Iterator<String> it = stringIt; it.hasNext();) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		for (; stringIt.hasNext();) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		while (stringList.iterator().hasNext()) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
		while (stringIt.hasNext()) {
			modelProvider.get(SimpleModel.class, resource);
			break;
		}
	}
}