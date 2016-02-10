Fields that are annotated with ``@JcrProperty`` should not be accessed from within constructor. Setting value to those fields will have no effect because they will be overwritten by Slice. Invoking methods on those fields will cause NullPointerException.

== Noncompliant Code Example
``
@SliceResource
public class JcrPropertySampleModel {
	@JcrProperty
	private String name;

	private int length;

	public JcrPropertySampleModel() {
	    length = name.length();
	}

	public JcrPropertySampleModel(String name) {
	    this.name = name;
	}

	public int getLength() {
	    return length;
	}
}
``

== Compliant Solution
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

``
@SliceResource
public class JcrPropertySampleModel  implements InitializableModel {
	@JcrProperty
	private String name;

	private int length;

	@Override
	public void afterCreated() {
	    length = name.length();
	}

	public int getLength() {
	    return length;
	}
}
``