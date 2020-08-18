package com.heaven7.java.validate.validator;

import com.heaven7.java.base.util.TextUtils;
import com.heaven7.java.validate.Comparators;
import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.RangeValidator;
import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.ValidateDate;
import com.heaven7.java.validate.parser.NumberParser;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;

public class DateValidator implements Validator {

    private final NumberParser parser = new NumberParser();

    @Override
    public boolean accept(Object context, Object input, Annotation annotation) {
        try {
            if (annotation instanceof ValidateDate ) {
                ValidateDate vd = (ValidateDate) annotation;
                if(vd.rangeValidator() != RangeValidator.class && !TextUtils.isEmpty(vd.rangeExpre())){
                    Object val = input;
                    if(input instanceof String && context instanceof DateContext){
                        val = new SimpleDateFormat(((DateContext) context).getDateTemplate()).parse((String) input).getTime();
                    }
                    return vd.rangeValidator().newInstance().accept(context, vd.rangeExpre(), val, parser, Comparators.NUMBER);
                }
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
