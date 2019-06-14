HTL <sly> elements are automatically unwrapped.

== Noncompliant Code Example
``
<sly data-sly-test="${myTest}" data-sly-unwrap>Caption</sly>
``
== Compliant Solution
``
<sly data-sly-test="${myTest}">Caption</sly>

<sly data-sly-test="${myTest}" data-sly-unwrap="${false}">Caption</sly>
``
