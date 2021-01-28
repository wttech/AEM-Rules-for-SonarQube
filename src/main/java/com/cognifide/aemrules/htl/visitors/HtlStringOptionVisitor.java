/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Wunderman Thompson Technology
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
package com.cognifide.aemrules.htl.visitors;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.scripting.sightly.compiler.expression.NodeVisitor;
import org.apache.sling.scripting.sightly.compiler.expression.nodes.*;

public class HtlStringOptionVisitor implements NodeVisitor<String> {
    @Override
    public String evaluate(PropertyAccess propertyAccess) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(Identifier identifier) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(StringConstant text) {
        return text.getText();
    }

    @Override
    public String evaluate(BinaryOperation binaryOperation) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(BooleanConstant booleanConstant) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(NumericConstant numericConstant) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(UnaryOperation unaryOperation) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(TernaryOperator ternaryOperator) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(RuntimeCall runtimeCall) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(MapLiteral mapLiteral) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(ArrayLiteral arrayLiteral) {
        return StringUtils.EMPTY;
    }

    @Override
    public String evaluate(NullLiteral nullLiteral) {
        return StringUtils.EMPTY;
    }
}
