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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.config.Configuration;

public final class HtlFilePredicateProvider {

    private HtlFilePredicateProvider() {
        // private constructor to hide public one
    }

    public static FilePredicate createFilePredicate(Configuration configuration, SensorContext sensorContext) {
        FilePredicates predicates = sensorContext.fileSystem().predicates();

        List<FilePredicate> fileExtensions = getFileExtensionsPredicates(predicates, configuration);
        List<FilePredicate> relativePaths = getPathsPredicate(predicates, configuration);

        return predicates.and(
            predicates.hasType(InputFile.Type.MAIN),
            predicates.or(
                predicates.hasLanguages(Htl.KEY),
                predicates.or(fileExtensions)
            ),
            predicates.or(relativePaths)
        );
    }

    private static List<FilePredicate> getFileExtensionsPredicates(FilePredicates predicates, Configuration configuration) {
        return Stream.of(configuration.getStringArray(Constants.FILE_EXTENSIONS_PROP_KEY))
            .filter(Objects::nonNull)
            .map(extension -> StringUtils.removeStart(extension, "."))
            .map(predicates::hasExtension)
            .collect(Collectors.toList());
    }

    private static List<FilePredicate> getPathsPredicate(FilePredicates predicates, Configuration configuration) {
        return Stream.of(configuration.getStringArray(Constants.HTL_FILES_RELATIVE_PATHS_KEY))
            .filter(StringUtils::isNotEmpty)
            .map(path -> path.concat("/**"))
            .map(predicates::matchesPathPattern)
            .collect(Collectors.toList());
    }

}
