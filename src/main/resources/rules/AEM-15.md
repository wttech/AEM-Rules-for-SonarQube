Usage of ``synchronized`` keyword should be avoided if possible. Check if using ``synchronized`` can be replaced with more sophisticated solution.

== Noncompliant Code Example

``
import java.util.ArrayList;
import java.util.List;
(...)
private final List<String> list = new ArrayList<>(16);

public synchronized void addElement(String s) { // Noncompliant
	list.add(s);
}
``
or
``
import java.util.ArrayList;
import java.util.List;
(...)
private final List<String> list = new ArrayList<>(16);

public void removeElement(String s) {
	synchronized(list) { // Noncompliant
		list.remove(s);
	}
}
``
or
``
private int counter = 0;

public synchronized void increment() { // Noncompliant
	counter++;
}
``

== Compliant Solution

``
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
(...)
private final List<String> list = new CopyOnWriteArrayList<>(16);
// use java.util.concurrent.ConcurrentHashMap for Map

public void addElement(String s) {
	list.add(s);
}
``
or
``
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
(...)
private final List<String> list = Collections.synchronizedList(new ArrayList<>(16));
// Collections object contains also synchronizedMap function

public void addElement(String s) {
	list.add(s);
}
``
or
``
import java.util.concurrent.atomic.AtomicInteger
java.util.concurrent.atomic.LongAdder // from java 8
(...)
private final AtomicInteger counter = new AtomicInteger(0);
private final LongAdder meter = new LongAdder();

public void increment() {
	counter.addAndGet(1);
	meter.increment();
}
``

== See
More details:
[Java 7 java.util.concurrent javadoc](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/package-summary.html)
[Java 8 java.util.concurrent javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html)