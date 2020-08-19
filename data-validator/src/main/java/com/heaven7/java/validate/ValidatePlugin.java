package com.heaven7.java.validate;

import java.lang.annotation.Annotation;

public interface ValidatePlugin {

    String getMessage(Annotation anno);
    Validator getValidator(Annotation anno);

    /**
     * get the cache key (often is classname.hash) , which will be cache if return non-null.
     * @param anno the annotation
     * @return the cache key
     */
    Integer getCacheKey(Annotation anno);
    int getOrder(Annotation anno);

}
