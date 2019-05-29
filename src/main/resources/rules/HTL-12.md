Use the most restrictive HTL context possible.

== Noncompliant Code Example
```
<div data-index-number=${model.index} >
```
    
== Compliant Solution
```
<div data-index-number=${model.index @ context='number'} >
```