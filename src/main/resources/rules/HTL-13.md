Avoid using 'unsafe' display context, it disables XSS protection completely.

== Noncompliant Code Example
```
<div class="my-component>
    <p>${model.text @ context='unsafe'}</p>
</div>
```
    
== Compliant Solution
```
<div class="my-component>
    <p>${model.text}</p>
</div>
```
