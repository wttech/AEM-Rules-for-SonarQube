package com.cognifide.aemrules;

import com.cognifide.aemrules.extensions.AemRulesRulesDefinition;
import com.cognifide.aemrules.extensions.CheckListRegistrar;
import org.sonar.api.Plugin;

public class AemRulesSonarPlugin implements Plugin {

	@Override
	public void define(Context context) {
		context.addExtension(AemRulesRulesDefinition.class);
		context.addExtension(CheckListRegistrar.class);
	}



}
