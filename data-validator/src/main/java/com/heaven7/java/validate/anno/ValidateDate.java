package com.heaven7.java.validate.anno;

import com.heaven7.java.validate.RangeValidator;
import com.heaven7.java.validate.validator.SimpleRangeValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface ValidateDate {

    /**
     * the notice message if validate failed
     * @return the msg
     */
    String value();

    /**
     * the range expression . like '199 <= x <= 2000 '
     * @return the range expression
     */
    String rangeExpre() default "";

    /**
     * the range validator class
     * @return  the range validator class
     */
    Class<? extends RangeValidator> rangeValidator() default SimpleRangeValidator.class;

    /**
     * the order of member. min means validate first, that means 'AESC'.
     * @return the order
     */
    int order() default 1;
}
