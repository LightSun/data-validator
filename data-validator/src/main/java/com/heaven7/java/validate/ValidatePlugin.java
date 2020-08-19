package com.heaven7.java.validate;

import java.lang.annotation.Annotation;

/**
 * the validate plugin.
 */
public interface ValidatePlugin {

    /**
     * get notice message which will be used if validate failed
     * @param anno yje annotation
     * @return the message
     */
    String getMessage(Annotation anno);

    /**
     * get the validator. should never be null
     * @param anno the annotation
     * @return the validator
     */
    Validator getValidator(Annotation anno);
    /**
     * get the cache key (often is classname.hash) , which will be cache if return non-null.
     * @param anno the annotation
     * @return the cache key
     */
    Integer getCacheKey(Annotation anno);

    /**
     * get the order of validate
     * @param anno the annotation
     * @return the order
     */
    int getOrder(Annotation anno);

}
