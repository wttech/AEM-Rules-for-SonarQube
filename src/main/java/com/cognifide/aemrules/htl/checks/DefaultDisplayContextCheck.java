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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    private static final Map<String, List<String>> TAG_ATTRIBUTE_MAPPING = Map.ofEntries(
            Map.entry("a", List.of("href")),
            Map.entry("area", List.of("href")),
            Map.entry("audio", List.of("src")),
            Map.entry("base", List.of("href")),
            Map.entry("blockquote", List.of("cite")),
            Map.entry("button", List.of("formaction")),
            Map.entry("del", List.of("cite")),
            Map.entry("embed", List.of("src")),
            Map.entry("form", List.of("action")),
            Map.entry("html", List.of("manifest")),
            Map.entry("img", List.of("src")),
            Map.entry("ins", List.of("cite")),
            Map.entry("input", List.of("formaction", "src")),
            Map.entry("iframe", List.of("src")),
            Map.entry("link", List.of("href")),
            Map.entry("q", List.of("cite")),
            Map.entry("object", List.of("data")),
            Map.entry("video", List.of("poster")),
            Map.entry("script", List.of("src")),
            Map.entry("source", List.of("src")),
            Map.entry("track", List.of("src"))
    );

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
