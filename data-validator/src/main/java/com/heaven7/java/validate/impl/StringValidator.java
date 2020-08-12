package com.heaven7.java.validate.impl;

import com.heaven7.java.base.util.TextUtils;
import com.heaven7.java.validate.Validator;

public class StringValidator implements Validator {
    @Override
    public boolean accept(Object context, Object input) {
        return input instanceof String && !TextUtils.isEmpty(input.toString());
    }
}
