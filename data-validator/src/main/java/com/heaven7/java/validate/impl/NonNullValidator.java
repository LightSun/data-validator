package com.heaven7.java.validate.impl;

import com.heaven7.java.validate.Validator;

public class NonNullValidator implements Validator {
    @Override
    public boolean accept(Object context, Object input) {
        return input != null;
    }
}
