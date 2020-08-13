package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.Validator;

import java.lang.annotation.Annotation;

public class NonNullValidator implements Validator {
    @Override
    public boolean accept(Object context, Object input, Annotation annotation) {
        return input != null;
    }
}
