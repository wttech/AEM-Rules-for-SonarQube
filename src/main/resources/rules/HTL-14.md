HTL expressions in HTML comments should have defined context.

== Noncompliant Code Example

``
<!--[if IE]><link rel="shortcut icon" href="${site.root}/images/favicon/favicon.ico?v2"><![endif]-->
``
    
== Compliant Solution

``
<!--[if IE]><link rel="shortcut icon" href="${site.root @ context='uri'}/images/favicon/favicon.ico?v2"><![endif]-->
``
