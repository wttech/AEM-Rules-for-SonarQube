The [`page.getContentResource()`](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/reference-materials/javadoc/com/day/cq/wcm/api/Page.html#getContentResource())
is a method of the [`Page`](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/reference-materials/diff-previous/changes/com.day.cq.wcm.api.Page.html)
which allows you to get a Resource from the page.
It is possible to get a `null` if jcr:content does not exist.

Lack of jrc:content can be caused by not publishing the parent page.

You should always null check the return value of getContentResource()

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
	return return ValueMap.EMPTY;
}
```
