The [`SlingQuery`](https://sling.apache.org/documentation/bundles/sling-query.html)
is a Sling resource tree traversal tool inspired by the jQuery JavaScript API.

`SearchStrategy` can have negative performance impact if mismatched.
Therefore developer should always make informed decision and define strategy explicitly.

== Noncompliant Code Example

```
private void strategyNotDefined(Resource resource) {
		SlingQuery sq = $(resource);
		sq.find();
	}
```

== Compliant Solution

```
private void strategyDefined(Resource resource) {
		SlingQuery sq = $(resource).searchStrategy(SearchStrategy.BFS);
		sq.find();
	}
```
