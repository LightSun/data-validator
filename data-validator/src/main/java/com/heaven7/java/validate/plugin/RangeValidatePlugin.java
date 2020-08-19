package com.heaven7.java.validate.plugin;

import com.heaven7.java.validate.RangeValidator;
import com.heaven7.java.validate.ValidatePlugin;
import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.ValidateRange;
import com.heaven7.java.validate.validator.CommonRangeValidator;

import java.lang.annotation.Annotation;
import java.util.Comparator;

/**
 * range validate plugin
 * @since 1.0.2
 */
public class RangeValidatePlugin implements ValidatePlugin {

    private final RangeValidator.Parser expreParser;
    private final Comparator comparator;

    public RangeValidatePlugin(RangeValidator.Parser expreParser, Comparator comparator) {
        this.expreParser = expreParser;
        this.comparator = comparator;
    }

    @Override
    public String getMessage(Annotation anno) {
        ValidateRange vd = (ValidateRange) anno;
        return vd.value();
    }

    @Override
    public Validator getValidator(Annotation anno) {
        return new CommonRangeValidator(expreParser, comparator);
    }

    @Override
    public Integer getCacheKey(Annotation anno) {
        return ("RangeValidatePlugin: " + expreParser.getClass().getName() + comparator.getClass().getName()).hashCode();
    }

    @Override
    public int getOrder(Annotation anno) {
        ValidateRange vd = (ValidateRange) anno;
        return vd.order();
    }
}
