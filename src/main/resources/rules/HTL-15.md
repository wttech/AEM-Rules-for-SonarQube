Use Camel Case in identifiers:
 * variable names
 * template names

== Noncompliant Code Example

``
<sly data-sly-template.COMPONENT_VALIDATION>
<sly data-sly-use.GalleryModel="com.example.GalleryModel">
``

== Compliant Solution

``
<sly data-sly-template.componentValidation>
<sly data-sly-use.galleryModel="com.example.GalleryModel">
``