import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

@Component
@Service
public class S {

	private Object lockObj = new Object();

	private String s;

	public void setS(String s) {
		this.s = s; // Noncompliant {{Unsynchronized modification of a Service field is not thread safe.}}
	}

	@Activate
	protected void activate(final ComponentContext context) {
		s = "Test"; // Compliant; in @Activate method
	}

	@Deactivate
	protected void deactivate(final ComponentContext context) {
		s = "Test"; // Compliant; in @Deativate method
	}

	public synchronized void setSS() {
		s = "Test"; // Compliant
	}

	private void doSomething() {
		synchronized (lockObj) {
			s = "Test"; // Compliant
		}
	}
}
class NotService {
	private String field;

	public void setField(String field) {
		this.field = field; // Compliant; Class in not Service
	}
}