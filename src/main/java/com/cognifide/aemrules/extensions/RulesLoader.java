/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.extensions;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RuleParamType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.api.utils.FieldUtils2;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaCheck;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

public class RulesLoader {

    private static final Logger LOG = LoggerFactory.getLogger(RulesLoader.class);

    private static final String RULE_DESCRIPTION_FOLDER = "rules";
    private static final String RULE_DESCRIPTION_EXTENSION = "md";
    private static final String RULE_METADATA_FOLDER = "metadata";
    private static final String RULE_METADATA_EXTENSION = "json";

    private final Gson gson = new Gson();

    private static final Function<Class<?>, RuleParamType> TYPE_FOR_CLASS = Functions.forMap(
        ImmutableMap.<Class<?>, RuleParamType>builder()
            .put(Integer.class, RuleParamType.INTEGER)
            .put(int.class, RuleParamType.INTEGER)
            .put(Float.class, RuleParamType.FLOAT)
            .put(float.class, RuleParamType.FLOAT)
            .put(Boolean.class, RuleParamType.BOOLEAN)
            .put(boolean.class, RuleParamType.BOOLEAN)
            .build(),
        RuleParamType.STRING
    );

    public void load(RulesDefinition.NewExtendedRepository repo, List<Class<? extends JavaCheck>> annotatedClasses) {
        for (Class<? extends JavaCheck> annotatedClass : annotatedClasses) {
            loadRule(repo, annotatedClass);
        }
    }

    @CheckForNull
    RulesDefinition.NewRule loadRule(RulesDefinition.NewExtendedRepository repo, Class<? extends JavaCheck> clazz) {
        Rule ruleAnnotation = AnnotationUtils.getAnnotation(clazz, Rule.class);
        if (ruleAnnotation != null) {
            return loadRule(repo, clazz, ruleAnnotation);
        } else {
            LOG.warn("The class {} should be annotated with {}", clazz.getCanonicalName(), Rule.class);
            return null;
        }
    }

    private RulesDefinition.NewRule loadRule(RulesDefinition.NewExtendedRepository repo, Class<? extends JavaCheck> clazz, Rule ruleAnnotation) {
        String ruleKey = StringUtils.defaultIfEmpty(ruleAnnotation.key(), clazz.getCanonicalName());
        String ruleName = StringUtils.defaultIfEmpty(ruleAnnotation.name(), null);
        String description = StringUtils.defaultIfEmpty(loadDescription(ruleKey), "No description yet.");

        RulesDefinition.NewRule rule = repo.createRule(ruleKey);
        rule.setName(ruleName).setMarkdownDescription(description);
        rule.setSeverity(ruleAnnotation.priority().name());
        rule.setStatus(RuleStatus.valueOf(ruleAnnotation.status()));
        rule.setTags(ruleAnnotation.tags());

        addMetadata(rule);

        List<Field> fields = FieldUtils2.getFields(clazz, true);
        for (Field field : fields) {
            loadParameters(rule, field);
        }

        return rule;
    }

    private void addMetadata(RulesDefinition.NewRule rule) {
        String json = loadMetadata(rule.key());
        if (json != null) {
            RuleMetadata ruleMetadata = gson.fromJson(json, RuleMetadata.class);
            rule.setDebtRemediationFunction(ruleMetadata.remediation.remediationFunction(rule.debtRemediationFunctions()));
        }
    }

    protected String loadDescription(String ruleKey) {
        return loadResource(RULE_DESCRIPTION_FOLDER, ruleKey, RULE_DESCRIPTION_EXTENSION);
    }

    protected String loadMetadata(String ruleKey) {
        return loadResource(RULE_METADATA_FOLDER, ruleKey, RULE_METADATA_EXTENSION);
    }

    protected String loadResource(String resourceFolder, String ruleKey, String fileExtension) {
        String result = null;
        try {
            String path = String.format("/%s/%s.%s", resourceFolder, ruleKey, fileExtension);
            URL url = Resources.getResource(RulesLoader.class, path);
            result = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException | IllegalArgumentException e) {
            LOG.error("Cannot read resource file.", e);
        }
        return result;
    }

    private void loadParameters(RulesDefinition.NewRule rule, Field field) {
        org.sonar.check.RuleProperty propertyAnnotation = field.getAnnotation(org.sonar.check.RuleProperty.class);
        if (propertyAnnotation != null) {
            String fieldKey = StringUtils.defaultIfEmpty(propertyAnnotation.key(), field.getName());
            RulesDefinition.NewParam param = rule.createParam(fieldKey)
                .setDescription(propertyAnnotation.description())
                .setDefaultValue(propertyAnnotation.defaultValue());

            if (!StringUtils.isBlank(propertyAnnotation.type())) {
                try {
                    param.setType(RuleParamType.parse(propertyAnnotation.type().trim()));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid property type [" + propertyAnnotation.type() + "]", e);
                }
            } else {
                param.setType(guessType(field.getType()));
            }
        }
    }

    @VisibleForTesting
    static RuleParamType guessType(Class<?> type) {
        return TYPE_FOR_CLASS.apply(type);
    }

    private static class RuleMetadata {

        Remediation remediation;
    }

    private static class Remediation {

        String constantCost;

        public DebtRemediationFunction remediationFunction(RulesDefinition.DebtRemediationFunctions remediationFn) {
            return remediationFn.constantPerIssue(constantCost);
        }
    }
}
