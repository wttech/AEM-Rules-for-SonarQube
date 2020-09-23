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
package com.cognifide.aemrules.htl.checks;

import com.cognifide.aemrules.metadata.Metadata;
import com.cognifide.aemrules.tag.Tags;
import com.cognifide.aemrules.version.AemVersion;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.html.node.TagNode;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rule(
        key = DefaultDisplayContextCheck.RULE_KEY,
        name = DefaultDisplayContextCheck.RULE_MESSAGE,
        priority = Priority.MINOR,
        tags = Tags.AEM
)
@AemVersion(
        from = "6.0"
)
@Metadata(
        technicalDebt = "5min"
)
public class DefaultDisplayContextCheck extends AbstractHtlCheck {

    public static final String RULE_KEY = "HTL-6";

    public static final String RULE_MESSAGE = "HTL uses uri display context as default for src, poster, manifest, href, formaction, data, cite, action attributes";

    private static final Pattern CONTEXT_URI_DEFINITION = Pattern.compile("context=['\"]uri['\"]");

    private static final String VIOLATION_MESSAGE = "Explicitly using default display context, please remove display context from expression";

    private static final Map<String, List<String>> TAG_ATTRIBUTE_MAPPING = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>("a", Arrays.asList("href")),
            new AbstractMap.SimpleImmutableEntry<>("area", Arrays.asList("href")),
            new AbstractMap.SimpleImmutableEntry<>("audio", Arrays.asList("src")),
            new AbstractMap.SimpleImmutableEntry<>("base", Arrays.asList("href")),
            new AbstractMap.SimpleImmutableEntry<>("blockquote", Arrays.asList("cite")),
            new AbstractMap.SimpleImmutableEntry<>("button", Arrays.asList("formaction")),
            new AbstractMap.SimpleImmutableEntry<>("del", Arrays.asList("cite")),
            new AbstractMap.SimpleImmutableEntry<>("embed", Arrays.asList("src")),
            new AbstractMap.SimpleImmutableEntry<>("form", Arrays.asList("action")),
            new AbstractMap.SimpleImmutableEntry<>("html", Arrays.asList("manifest")),
            new AbstractMap.SimpleImmutableEntry<>("img", Arrays.asList("src")),
            new AbstractMap.SimpleImmutableEntry<>("ins", Arrays.asList("cite")),
            new AbstractMap.SimpleImmutableEntry<>("input", Arrays.asList("formaction", "src")),
            new AbstractMap.SimpleImmutableEntry<>("iframe", Arrays.asList("src")),
            new AbstractMap.SimpleImmutableEntry<>("link", Arrays.asList("href")),
            new AbstractMap.SimpleImmutableEntry<>("q", Arrays.asList("cite")),
            new AbstractMap.SimpleImmutableEntry<>("object", Arrays.asList("data")),
            new AbstractMap.SimpleImmutableEntry<>("video", Arrays.asList("poster")),
            new AbstractMap.SimpleImmutableEntry<>("script", Arrays.asList("src")),
            new AbstractMap.SimpleImmutableEntry<>("source", Arrays.asList("src")),
            new AbstractMap.SimpleImmutableEntry<>("track", Arrays.asList("src"))
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Override
    public void startElement(TagNode node) {
        String nodeName = node.getNodeName();
        if (TAG_ATTRIBUTE_MAPPING.containsKey(nodeName)) {
            Collection<String> supportedAttributes = TAG_ATTRIBUTE_MAPPING.get(nodeName);
            node.getAttributes().stream()
                    .filter(attribute -> supportedAttributes.contains(attribute.getName()))
                    .filter(a -> CONTEXT_URI_DEFINITION.matcher(a.getValue()).find())
                    .forEach(a -> createViolation(node.getStartLinePosition(), VIOLATION_MESSAGE));
        }
    }
}
