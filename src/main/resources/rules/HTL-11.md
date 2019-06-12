Use existing HTML elements instead of adding extra sly tags.

== Noncompliant Code Example
``
<sly data-sly-test="${model.visible}" data-sly-use="com.example.HeroModel">
    <div class="myComponent">
        <h1>${model.headline}</h1>
        <p>${model.text}</p>
    </div>
</sly>
``

== Compliant Solution
``
<div class="myComponent"
     data-sly-test="${model.visible}"
     data-sly-use="com.example.HeroModel">
    <h1>${model.headline}</h1>
    <p>${model.text}</p>
</div>
``
