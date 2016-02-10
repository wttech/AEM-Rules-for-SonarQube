Objects annotated by ``@SliceResource`` should not use any session based objects, except places like constructor and overridden ``com.cognifide.slice.api.model.InitializableModel.afterCreated()`` method.
Classes dependent on session live as long as the original session is opened. Using for example ``ResourceResolver`` outside a constructor or ``afterCreated`` method may result in ``IllegalStateException`` (when ``ResourceResolver`` was already closed) or not refreshed session warning.

== Noncompliant Code Example
``
@SliceResource
public class SampleModel {

	public String getAttribute() {
		return (String) resolver.getAttribute("TEST"); // Noncompliant {{Objects annotated by @SliceResource should not use (except: constructor, com.cognifide.slice.api.model.InitializableModel.afterCreated()) and return any session based object.}}
	}

	public ResourceResolver getResolver() {
		return resolver; // Noncompliant
	}
}
``

== Compliant Solution
``
@SliceResource
public class SampleModel implements InitializableModel {

	private final String attribute;

	@Override
	public void afterCreated() {
		attribute = (String) resolver.getAttribute("TEST");
	}
}
``