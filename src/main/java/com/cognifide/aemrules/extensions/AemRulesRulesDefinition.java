package com.cognifide.aemrules.extensions;

import org.sonar.api.server.rule.RulesDefinition;

public class AemRulesRulesDefinition implements RulesDefinition {

	@Override
	public void define(Context context) {
		NewRepository repo = context.createRepository(CheckListRegistrar.REPOSITORY_KEY, "java");
		repo.setName(CheckListRegistrar.REPOSITORY_KEY);
		RulesLoader rulesLoader = new RulesLoader();
		rulesLoader.load(repo, CheckListRegistrar.getCheckClasses());
		repo.done();
	}

}
