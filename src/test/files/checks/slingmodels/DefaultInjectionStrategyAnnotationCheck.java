package com.example;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import java.lang.String;


@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DefaultInjectionStrategyAnnotationCheck {

	@Inject
	@Optional // Noncompliant
	private String str1;

	@Inject
	private String str2;

	@Inject
	@Optional // Noncompliant
	private String str3;

	public String getStr1() {
		return str1;
	}

	public String getStr2() {
		return str2;
	}
	public String getStr3() {
		return str3;
	}

}
