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
     * the range expression . like '{} <= x <= {} '
     * @return the range expression
     */
    String rangeExpre() default "";

    /**
     * the range validator class
     * @return  the range validator class
     */
    Class<? extends RangeValidator> rangeValidator() default SimpleRangeValidator.class;
}
