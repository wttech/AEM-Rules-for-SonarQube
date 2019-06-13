Always try to re-use existing conditions, so the code is more readable.

== Noncompliant Code Example

``
<span class="uber-mode__top-bar" data-sly-test="${uberModeHelper.uberModeEnabled || forceUberMode}">
    <div class="my-component">
        Some text
    </div>
</span>
<span class="uber-mode__bottom-bar" data-sly-test="${uberModeHelper.uberModeEnabled || forceUberMode}"></span>
``
    
== Compliant Solution

``
<span class="uber-mode__top-bar" data-sly-test.uberMode="${uberModeHelper.uberModeEnabled || forceUberMode}">
    <div class="my-component">
        Some text
    </div>
</span>
<span class="uber-mode__bottom-bar" data-sly-test="${uberMode}"></span>
``
