Always Place HTL Attributes After the Ones that are Part of the Markup
It's easier to understand HTL code if the attributes processed server-side are visually separated from the ones used at the front-end.

== Noncompliant Code Example
``
<div data-sly-test="${model.enabled}" class="decorated" data-sly-unwrap="${!model.decorated}"> // Bad - mixing HTL attributes with other ones
``
== Compliant Solution
``
<!--/* Good - HTL-specific attributes grouped at the end of the element */ -->
<div class="decorated" data-sly-test="${model.enabled}" data-sly-unwrap="${!model.decorated}"> // Good - HTL-specific attributes grouped at the end of the element
``