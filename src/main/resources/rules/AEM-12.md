Fields that are annotated with @JcrProperty should not be access from within constructor.

== Example

=== Noncompliant Code Example
``
@SliceResource
public class JcrPropertySampleModel {
	@JcrProperty
	private String name;

	private int length;

	public JcrPropertySampleModel() {
	    length = name.length();
	}

	public int getLength() {
	    return length;
	}
}
``

=== Compliant Solution
``
@SliceResource
public class JcrPropertySampleModel {
	@JcrProperty
	private String name;

	public int getLength() {
	    return name.length();
	}
}
``