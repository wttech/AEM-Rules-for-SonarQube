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
