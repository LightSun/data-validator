package com.heaven7.java.validate;

import java.lang.annotation.Annotation;

public interface ValidatePlugin {

    String getMessage(Annotation anno);
    Class<?> getValidatorClass(Annotation anno);
    int getOder(Annotation anno);
}
