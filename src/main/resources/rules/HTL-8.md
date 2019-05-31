In HTL event attributes must contain display context definition";

== Noncompliant Code Example

```
<a href="#id" onclick="${myFunctionName}()">Link</a> <!--/* Non-Compliant */-->
```

== Compliant Solution

```
<a href="#id" onclick="${myFunctionName @ context='scriptToken'}()">Link</a>
```
