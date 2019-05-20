The [`ModifiableValueMap`](https://sling.apache.org/apidocs/sling7/org/apache/sling/api/resource/ModifiableValueMap.html) is an extension
of the `ValueMap` which allows to modify and persist properties.
It is checking user permissions and returns `null` if the user has no permission to write.

The modifiable value map is only changeable through any of the following methods:
- `put(String, Object)`
- `putAll(java.util.Map)`
- `remove(Object)`

If none of the above methods is called, `ValueMap` should be used instead.

== Noncompliant Code Example

```
public Object getProperty(Resource resource) {
	ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class); // Noncompliant ValueMap should be used
	return createdResourceProperties.get("propertyName");
}
```

== Compliant Solution

```
public Object getProperty(Resource resource) {
	ValueMap createdResourceProperties = resource.getValueMap();
	return createdResourceProperties.get("propertyName");
}
```
