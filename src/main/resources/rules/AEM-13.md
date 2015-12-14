Since Services are singletons, any unsynchronized modification of a Service field is not thread safe. Such modification should be synchronized or done within methods annotated with ``@Activate`` or ``@Deactivate``.
== Noncompliant Code Example
``
@Component
@Service
public class S {

	private String s;

	public void setS(String s) {
        this.s = s; // Noncompliant; unsynchronized modification of a Service field is not thread safe.
    }
``
== Compliant Solution
``
    // ...
  	public synchronized void setS(String s) {
  		this.s = s;
  	}
``
``
    // ...
    @Activate
  	public synchronized void setS(String s) {
  		this.s = s;
  	}
``
``
    // ...
  	public void setS(String s) {
        synchronized (lockObj) {
            this.s = s;
        }
    }
``