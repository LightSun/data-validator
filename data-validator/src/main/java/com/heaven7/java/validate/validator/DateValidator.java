package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.ValidateDate;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;

public class DateValidator implements Validator {

    @Override
    public boolean accept(Object context, Object input, Annotation annotation) {
        try {
            if (annotation instanceof ValidateDate) {
                ValidateDate vd = (ValidateDate) annotation;
                return vd.rangeValidator().newInstance().accept(context, vd.rangeExpre(), input);
            }
            if(input instanceof String && context instanceof DateContext){
                new SimpleDateFormat(((DateContext) context).getDateTemplate()).parse((String) input);
                return true;
            }
        }catch (Exception e) {
            return false;
        }
        return false;
    }
}
