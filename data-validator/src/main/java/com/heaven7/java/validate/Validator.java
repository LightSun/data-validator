package com.heaven7.java.validate;

import java.lang.annotation.Annotation;

public interface Validator {

    /**
     * indicate the validate success or not. true if success.
     * @param context the context
     * @param input the value to check
     * @param annotation the annotation
     * @return true if accept.
     */
    boolean accept(Object context, Object input, Annotation annotation);
}
