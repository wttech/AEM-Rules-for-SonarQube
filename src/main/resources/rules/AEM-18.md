The [`page.getContentResource()`](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/reference-materials/javadoc/com/day/cq/wcm/api/Page.html#getContentResource())
is a method of the [`Page` class](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/reference-materials/diff-previous/changes/com.day.cq.wcm.api.Page.html)
which allows you to get a Resource from the page.
It is possible to get a `null` if a jcr:content node does not exist in the repository.

Lack of 'jrc:content' is a quite common situation and occurs when one publishes a deeply nested page without publishing its parents first.
In that case parent pages will be published without `jcr:content` node.

Therefore, you should always null check the return value of 'getContentResource()'

== Noncompliant Code Example

```
public ValueMap getProperty(Page page) {
	return page.getContentResource().getValueMap();
}
```

== Compliant Solution

```
public ValueMap getProperty(Page page) {
	if (page.getContentResource != null) {
	  return page.getContentResource.getValueMap();
	}
	return ValueMap.EMPTY;
}
```

or

```
public ValueMap getProperty(Page page) {
    Optional<Resource> contentResource = Optional.ofNullable(page.getContentResource());
    return contentResource.map(Resource::getValueMap).orElse(ValueMap.EMPTY);
}
```
