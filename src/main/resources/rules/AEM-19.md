The [`SlingQuery`](https://sling.apache.org/documentation/bundles/sling-query.html)
is a Sling resource tree traversal tool inspired by the jQuery JavaScript API.

SearchStrategy should be always defined before using 'find()' method

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
