package com.heaven7.java.validate.anno;


import java.lang.annotation.*;

/**
 * the sort desc as 'AESC'.
 * @since 1.0.2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Sort {
    int order() default 0;
}
