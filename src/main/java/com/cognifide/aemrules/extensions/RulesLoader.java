/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Cognifide Limited
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

import com.cognifide.aemrules.metadata.Metadata;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.CheckForNull;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.server.rule.RuleParamType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.DebtRemediationFunctions;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.api.utils.FieldUtils2;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;


public class RulesLoader {

    private static final Logger LOG = LoggerFactory.getLogger(RulesLoader.class);

    private static final String RULE_DESCRIPTION_FOLDER = "rules";

    private static final String RULE_DESCRIPTION_EXTENSION = "md";

    private static final Map<Class<?>, RuleParamType> TYPE_FOR_CLASS = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Integer.class, RuleParamType.INTEGER),
            new AbstractMap.SimpleImmutableEntry<>(int.class, RuleParamType.INTEGER),
            new AbstractMap.SimpleImmutableEntry<>(Float.class, RuleParamType.FLOAT),
            new AbstractMap.SimpleImmutableEntry<>(float.class, RuleParamType.FLOAT),
            new AbstractMap.SimpleImmutableEntry<>(Boolean.class, RuleParamType.BOOLEAN),
            new AbstractMap.SimpleImmutableEntry<>(boolean.class, RuleParamType.BOOLEAN)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static RuleParamType guessType(Class<?> type) {
        return TYPE_FOR_CLASS.getOrDefault(type, RuleParamType.STRING);
    }

    public <T> void load(RulesDefinition.NewExtendedRepository repo, List<Class<? extends T>> annotatedClasses) {
        for (Class annotatedClass : annotatedClasses) {
            loadRule(repo, annotatedClass);
        }
    }

    @CheckForNull
    private RulesDefinition.NewRule loadRule(RulesDefinition.NewExtendedRepository repo, Class clazz) {
        return Optional.ofNullable(AnnotationUtils.getAnnotation(clazz, Rule.class))
            .map(annotationRule -> createRule(repo, clazz, annotationRule))
            .orElse(null);
    }

    private RulesDefinition.NewRule createRule(RulesDefinition.NewExtendedRepository repo, Class clazz, Rule ruleAnnotation) {
        String ruleKey = StringUtils.defaultIfEmpty(ruleAnnotation.key(), clazz.getCanonicalName());
        String ruleName = StringUtils.defaultIfEmpty(ruleAnnotation.name(), null);
        String description = StringUtils.defaultIfEmpty(loadDescription(ruleKey), "No description yet.");

        RulesDefinition.NewRule rule = repo.createRule(ruleKey);
        rule.setName(ruleName).setMarkdownDescription(description);
        rule.setSeverity(ruleAnnotation.priority().name());
        rule.setStatus(RuleStatus.valueOf(ruleAnnotation.status()));
        rule.setTags(ruleAnnotation.tags());

        setMetadata(rule, clazz);

        List<Field> fields = FieldUtils2.getFields(clazz, true);
        for (Field field : fields) {
            loadParameters(rule, field);
        }

        return rule;
    }

    private void setMetadata(RulesDefinition.NewRule rule, Class clazz) {
        Optional.ofNullable(AnnotationUtils.getAnnotation(clazz, Metadata.class))
            .ifPresent(metadataAnnotation -> setTechnicalDebt(rule, metadataAnnotation));
    }

    private void setTechnicalDebt(RulesDefinition.NewRule rule, Metadata metadataAnnotation) {
        String technicalDebt = metadataAnnotation.technicalDebt();
        if (StringUtils.isNotEmpty(technicalDebt)) {
            DebtRemediationFunctions remediationFunction = rule.debtRemediationFunctions();
            rule.setDebtRemediationFunction(remediationFunction.constantPerIssue(technicalDebt));
        }
    }

    private String loadDescription(String ruleKey) {
        return loadResource(RULE_DESCRIPTION_FOLDER, ruleKey, RULE_DESCRIPTION_EXTENSION);
    }

    private String loadResource(String resourceFolder, String ruleKey, String fileExtension) {
        String result = null;
        try {
            String path = String.format("/%s/%s.%s", resourceFolder, ruleKey, fileExtension);
            URL url = getClass().getResource(path);
            result = IOUtils.toString(url, StandardCharsets.UTF_8);
        } catch (IOException | IllegalArgumentException e) {
            LOG.error("Cannot read resource file.", e);
        }
        return result;
    }

    private void loadParameters(RulesDefinition.NewRule rule, Field field) {
        RuleProperty propertyAnnotation = field.getAnnotation(RuleProperty.class);
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

}
