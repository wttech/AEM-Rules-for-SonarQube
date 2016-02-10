Objects annotated by ``@SliceResource`` should not use any session based objects, except places like constructor and overridden ``com.cognifide.slice.api.model.InitializableModel.afterCreated()`` method.
Classes dependent on session live as long as the original session is opened. Using for example ``ResourceResolver`` outside a constructor or ``afterCreated`` method may result in ``IllegalStateException`` (when ``ResourceResolver`` was already closed) or not refreshed session warning.
Note that private methods using session based objects that are invoked from public context will be also marked as issue.

== Noncompliant Code Example
``
@SliceResource
public class SampleModel {

	public String getAttribute() {
		updateAttribute();
		return (String) resolver.getAttribute("TEST"); // Noncompliant {{Objects annotated by @SliceResource should not use or return any session based object, except in constructor or com.cognifide.slice.api.model.InitializableModel.afterCreated().}}
	}

	private void updateAttribute() {
		attribute = (String) resolver.getAttribute("TEST"); // Noncompliant
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
		updateAttribute();
	}

	private void updateAttribute() {
		attribute = (String) resolver.getAttribute("TEST");
	}

}
``