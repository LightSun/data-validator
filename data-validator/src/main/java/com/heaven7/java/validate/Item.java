package com.heaven7.java.validate;

public final class Item {

    public final String msg;
    public final Validator validator;
    public final Object value;

    public Item(String msg, Validator validator, Object val) {
        this.msg = msg;
        this.validator = validator;
        this.value = val;
    }
}