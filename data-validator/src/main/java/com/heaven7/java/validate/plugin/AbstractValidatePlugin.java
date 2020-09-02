package com.heaven7.java.validate.plugin;

import com.heaven7.java.validate.ValidatePlugin;
import com.heaven7.java.validate.anno.Sort;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public abstract class AbstractValidatePlugin implements ValidatePlugin {
    @Override
    public int getOrder(AnnotatedElement element, Annotation anno) {
        Sort sort = element.getAnnotation(Sort.class);
        return sort != null ? sort.value() : 0;
    }

}
