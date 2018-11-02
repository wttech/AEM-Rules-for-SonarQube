/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2018 Cognifide Limited
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
package com.cognifide.aemrules.htl;

import com.cognifide.aemrules.htl.api.HtlCheck;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.rule.RuleKey;

public class HtlChecks {

    private final CheckFactory checkFactory;

    private Set<Checks<HtlCheck>> checksByRepository = Sets.newHashSet();

    private HtlChecks(CheckFactory checkFactory) {
        this.checkFactory = checkFactory;
    }

    public static HtlChecks createHtlCheck(CheckFactory checkFactory) {
        return new HtlChecks(checkFactory);
    }

    public HtlChecks addChecks(String repositoryKey, Iterable<Class<? extends HtlCheck>> checkClass) {
        Checks<HtlCheck> htlChecks = checkFactory
            .<HtlCheck>create(repositoryKey)
            .addAnnotatedChecks(checkClass);
        checksByRepository.add(htlChecks);
        return this;
    }

    @Nullable
    public RuleKey ruleKeyFor(HtlCheck check) {
        RuleKey ruleKey = null;

        for (Checks<HtlCheck> checks : checksByRepository) {
            ruleKey = checks.ruleKey(check);

            if (ruleKey != null) {
                break;
            }
        }
        return ruleKey;
    }

    public List<HtlCheck> getAll() {
        return checksByRepository.stream()
            .map(Checks::all)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}
