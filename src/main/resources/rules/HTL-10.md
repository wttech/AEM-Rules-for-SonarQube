If it's not possible to use an existing element, prefer sly tags over introducing superfluous markup that renders on the page.

== Noncompliant Code Examples

``
<div data-sly-use.model="com.example.Model" data-sly-use.templates="templates.html"></div>
<div data-sly-use.model="com.example.Model" data-sly-use.templates="templates.html" data-sly-unwrap></div>
``

== Compliant Solution

``
<sly data-sly-use.model="com.example.Model" data-sly-use.templates="templates.html"></sly>
``
