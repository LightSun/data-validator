package com.heaven7.java.validate.anno;

import com.heaven7.java.validate.Validator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Validate{
    /**
     * the notice message if validate failed
     * @return the msg
     */
    String value();

    /**
     * the validator class to validate
     * @return the validator class. or default validator class.
     */
    Class<? extends Validator> validator() default Validator.class;
}
