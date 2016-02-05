package com.example;

import org.apache.commons.lang.StringUtils;

import com.cognifide.slice.mapper.annotation.JcrProperty;
import com.cognifide.slice.mapper.annotation.SliceResource;
import java.util.Properties;
import javax.inject.Inject;

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

	private String city;

	public JcrPropertySampleModel() {
	}

	public JcrPropertySampleModel(int i) {
		if (StringUtils.isBlank(name)) { // Noncompliant {{Fields annotated by @JcrProperty shouldn't be accessed from constructor.}}
			modName = surname + i; // Noncompliant
		}
		city = "Poznan";
	}
	
	@Inject
	public JcrPropertySampleModel(Properties properties) {
		city = properties.getProperty("city");
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
