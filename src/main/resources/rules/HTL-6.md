HTL uses uri display context as default for src, poster, manifest, href, formaction, data, cite, action attributes.
Explicit display context definition should be removed.

== Noncompliant Code Example

```
<img alt="${model.imageAlt @ context='attribute'}" src="${model.imageUrl @ context='uri'}" /> <!--/* Non-Compliant */-->
```

== Compliant Solution
```
<img alt="${model.imageAlt}" src="${model.imageUrl}" />
```

```
<div data-service-url="${model.url @ context='uri'}">
    ${'http://www.example.org/search?s=1&q=htl' @ context='uri'}
</div>
```
