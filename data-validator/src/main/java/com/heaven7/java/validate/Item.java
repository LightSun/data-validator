package com.heaven7.java.validate;

public final class Item {

    /**
     * the notice message
     */
    public final String msg;
    /**
     * the validator
     */
    public final Validator validator;
    /**
     * the value of this item. often is field value.
     */
    public final Object value;

    public Item(String msg, Validator validator, Object val) {
        this.msg = msg;
        this.validator = validator;
        this.value = val;
    }
}