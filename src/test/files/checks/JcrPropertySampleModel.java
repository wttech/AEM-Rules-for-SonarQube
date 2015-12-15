package com.example;

import org.apache.commons.lang.StringUtils;

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;

/**
 * @author Krzysztof Watral
 */
@SliceResource
public class JcrPropertySampleModel {

	@JcrProperty
	private String name;

	@JcrProperty
	private String surname;

	private String modName;

	public JcrPropertySampleModel() {
	}

	public JcrPropertySampleModel(int i) {
		if( StringUtils.isBlank(name) ) // Noncompliant {{Fields annotated by @JcrProperty shouldn't be access from constructor.}}
			modName = surname + i; // Noncompliant
	}

	public JcrPropertySampleModel(String surname) {
		this.surname = surname; // Noncompliant
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
