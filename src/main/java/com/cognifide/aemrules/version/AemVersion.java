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
package com.cognifide.aemrules.version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AEM version support indicator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AemVersion {

    /**
     * Sets the AEM version from which rule is supported (inclusive).
     */
    String from() default "";

    /**
     * Sets the AEM version to which rule is supported (inclusive).
     */
    String to() default "";

    /**
     * Indicates that all versions are supported.
     */
    boolean all() default false;

    /**
     * Sets AEM versions which are supported by rule
     */
    String[] included() default {};

    /**
     * Sets AEM versions which are not supported by rule
     */
    String[] excluded() default {};

}
