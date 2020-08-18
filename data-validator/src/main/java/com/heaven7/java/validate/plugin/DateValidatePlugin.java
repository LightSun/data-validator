package com.heaven7.java.validate.plugin;

import com.heaven7.java.validate.ValidatePlugin;
import com.heaven7.java.validate.anno.ValidateDate;
import com.heaven7.java.validate.validator.DateValidator;

import java.lang.annotation.Annotation;

public class DateValidatePlugin implements ValidatePlugin {

    @Override
    public String getMessage(Annotation anno) {
        ValidateDate vd = (ValidateDate) anno;
        return vd.value();
    }
    @Override
    public Class<?> getValidatorClass(Annotation anno) {
        return DateValidator.class;
    }

    @Override
    public int getOrder(Annotation anno) {
        ValidateDate vd = (ValidateDate) anno;
        return vd.order();
    }
}
