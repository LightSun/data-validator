package com.heaven7.java.validate.plugin;

import com.heaven7.java.validate.ValidatePlugin;
import com.heaven7.java.validate.anno.Validate;

import java.lang.annotation.Annotation;

public class CommonValidatePlugin implements ValidatePlugin {
    @Override
    public String getMessage(Annotation anno) {
        Validate validate = (Validate) anno;
        return validate.value();
    }

    @Override
    public Class<?> getValidatorClass(Annotation anno) {
        Validate validate = (Validate) anno;
        return validate.validator();
    }

    @Override
    public int getOder(Annotation anno) {
        Validate validate = (Validate) anno;
        return validate.order();
    }
}
