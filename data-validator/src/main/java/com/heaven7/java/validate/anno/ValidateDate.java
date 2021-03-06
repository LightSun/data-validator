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
    String expression() default "";

    /**
     * the range validator class
     * @return  the range validator class
     */
    Class<? extends RangeValidator> validator() default SimpleRangeValidator.class;

    /**
     * indicate null is permit or not
     * @return true if permit null
     * @since 1.0.2
     */
    boolean nullable() default false;
}
