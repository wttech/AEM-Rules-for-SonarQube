Always Place HTL Attributes After the Ones that are Part of the Markup
It's easier to understand HTL code if the attributes processed server-side are visually separated from the ones used at the front-end.

== Noncompliant Code Example
``
    <!--/* Bad - the same condition is evaluated multiple times */-->
<span class="uber-mode__top-bar" data-sly-test="${uberModeHelper.uberModeEnabled || forceUberMode}">
<div class="my-component">
    Blah blah blah
</div>
<span class="uber-mode__bottom-bar" data-sly-test="${uberModeHelper.uberModeEnabled || forceUberMode}">
``
 
 
    
== Compliant Solution
``
    <!--/* Good - condition defined in one place */-->
    <span class="uber-mode__top-bar" data-sly-test.uberMode="${uberModeHelper.uberModeEnabled || forceUberMode}">
    <div class="my-component">
        Blah blah blah
    </div>
    <span class="uber-mode__bottom-bar" data-sly-test="${uberMode}">L-specific attributes grouped at the end of the element
``