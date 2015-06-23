package com.cognifide.aemrules;

import com.cognifide.aemrules.extensions.AemRulesRulesDefinition;
import com.cognifide.aemrules.extensions.CheckListRegistrar;
import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

public class AemRulesSonarPlugin extends SonarPlugin {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getExtensions() {
		return Arrays.asList(AemRulesRulesDefinition.class, CheckListRegistrar.class);
	}

}
