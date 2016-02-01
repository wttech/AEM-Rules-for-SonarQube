Slice provides method for creating list of models from specified resources given as an ``Iterator``. Instead of iterating over resources yourself, use ``ModelProvider#getListFromResources`` method.
== Noncompliant Code Example
``
List<Model> models = new ArrayList<>();
while(resourcesIterator.hasNext()) // Noncompliant
    models.add(modelProvider.get(Model.class, resourcesIterator.next()));
}
``
``
List<Model> models = new ArrayList<>();
for (Iterator<Resource> it = resourcesIterator; it.hasNext();) { // Noncompliant
    models.add(modelProvider.get(Model.class, it.next()));
}
``
``
List<Model> models = new ArrayList<>();
for (Resource resource : resourcesCollection) { // Noncompliant
    models.add(modelProvider.get(Model.class, resource));
}
``
==Compliant Solution
``
List<Model> models = modelProvider.getListFromResources(Model.class, resourcesIterator);
``
``
List<Model> models = modelProvider.getListFromResources(Model.class, resourcesCollection.iterator());
``
==See
[ModelProvider Javadoc](https://github.com/Cognifide/Slice/blob/master/slice-core-api/src/main/java/com/cognifide/slice/api/provider/ModelProvider.java)