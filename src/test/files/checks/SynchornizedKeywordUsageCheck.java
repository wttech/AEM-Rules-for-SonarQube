package com.example;

import java.util.ArrayList;
import java.util.List;

public class SynchornizedKeywordUsageCheck {

	private final List<String> list = new ArrayList<>(16);

	public synchronized void addElement(String s) { // Noncompliant {{Usage of 'synchronized' keyword should be avoided if possible.}}
		list.add(s);
	}

	public void removeElement(String s) {
		synchronized (list) { // Noncompliant
			list.remove(s);
		}
	}

	private int counter = 0;

	public synchronized void increment() { // Noncompliant
		counter++;
	}

	@Override
	public synchronized void doubleIncrement() { // Noncompliant
		counter++;
		counter++;
	}

	@Override
	public synchronized void tripleIncrement() { // Noncompliant
		counter++;
		counter++;
		counter++;
	}

	public 
	synchronized // Noncompliant
	void
	quadrupleIncrement() {
		counter++;
		counter++;
		counter++;
		counter++;
	}
}
