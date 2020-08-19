package com.heaven7.java.validate.validator;

import com.heaven7.java.base.util.TextUtils;
import com.heaven7.java.validate.Comparators;
import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.RangeValidator;
import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.ValidateDate;
import com.heaven7.java.validate.parser.NumberDateParser;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;

public class DateValidator implements Validator {

    private final NumberDateParser parser = new NumberDateParser();

    @Override
    public boolean accept(Object context, Object input, Annotation annotation) {
        ValidateDate vd = (ValidateDate) annotation;
        if(input == null){
            return vd.nullable();
        }
        try {
            if(vd.rangeValidator() != RangeValidator.class && !TextUtils.isEmpty(vd.rangeExpre())){
                Object val = input;
                if(input instanceof String && context instanceof DateContext){
                    val = new SimpleDateFormat(((DateContext) context).getDateTemplate()).parse((String) input).getTime();
                }
                return vd.rangeValidator().newInstance().accept(context, vd.rangeExpre(), val, parser, Comparators.NUMBER);
            }
        }catch (Exception e) {
            return false;
        }
        return false;
    }
}
