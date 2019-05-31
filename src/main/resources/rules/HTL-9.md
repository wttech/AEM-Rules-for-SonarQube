For inline styles display context is mandatory

== Noncompliant Code Example

```
<a href="#id" style="color: ${colorName};">Link</a>
```

== Compliant Solution

```
<a href="#id" style="color: ${colorName @ context='styleToken'};">Link</a>
```
