Usage of ``@Optional`` is redundant, ``defaultInjectionStrategy`` is defined as ``OPTIONAL``

== Noncompliant Code Example

``
@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SampleModel {

@Inject
@Optional //Noncompliant
private String str1;
(...)
``
== Compliant Solution

``
@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SampleModel {

@Inject // Compliant
private String str1;
(...)
``
