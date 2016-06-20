The ModifiableValueMap is an extension of the ValueMap which allows to modify and persist properties.
It is checking user permissions and returns null if user has no permission to write.

Modifiable value map is only changeable through one of these methods:
put(String, Object)
putAll(java.util.Map)
remove(Object)

If none of above methods used, ValueMap should be used.

== Noncompliant Code Example

``
	public Object getProperty(Resource resource) {
		ModifiableValueMap createdResourceProperties = resource.adaptTo(ModifiableValueMap.class); // Noncompliant ValueMap should be used
		return createdResourceProperties.get("propertyName");
	}
``

== Compliant Solution

``
	public Object getProperty(Resource resource) {
		ValueMap createdResourceProperties = resource.getValueMap();
		return createdResourceProperties.get("propertyName");
	}
``
