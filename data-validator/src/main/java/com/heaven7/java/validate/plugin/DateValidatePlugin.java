package com.heaven7.java.validate.plugin;

import com.heaven7.java.validate.ValidatePlugin;
import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.ValidateDate;
import com.heaven7.java.validate.validator.DateValidator;

import java.lang.annotation.Annotation;

/**
 * Date validate plugin
 */
public class DateValidatePlugin implements ValidatePlugin {

    @Override
    public String getMessage(Annotation anno) {
        ValidateDate vd = (ValidateDate) anno;
        return vd.value();
    }

    @Override
    public Validator getValidator(Annotation anno) {
        return new DateValidator();
    }

    @Override
    public Integer getCacheKey(Annotation anno) {
        ValidateDate vd = (ValidateDate) anno;
        return (DateValidator.class.getName() + vd.validator().getName()).hashCode();
    }

    @Override
    public int getOrder(Annotation anno) {
        ValidateDate vd = (ValidateDate) anno;
        return vd.order();
    }
}
