package com.heaven7.java.validate.validator;

import com.heaven7.java.base.util.TextUtils;
import com.heaven7.java.validate.RangeValidator;
import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.ValidateRange;

import java.lang.annotation.Annotation;
import java.util.Comparator;

/**
 * the common range validator
 * @since 1.0.2
 */
public class CommonRangeValidator implements Validator {

    private final RangeValidator.Parser expreParser;
    private final Comparator comparator;

    public CommonRangeValidator(RangeValidator.Parser expreParser, Comparator comparator) {
        this.expreParser = expreParser;
        this.comparator = comparator;
    }

    @Override
    public boolean accept(Object context, Object input, Annotation annotation) {
        ValidateRange vr = (ValidateRange) annotation;
        if(input == null){
            return vr.nullable();
        }
        //no expression .permit
        if(TextUtils.isEmpty(vr.expression())){
            return true;
        }
        try {
            RangeValidator validator = vr.validator().newInstance();
            final Object val;
            if(input instanceof String){
                val = expreParser.parse(context, (String)input);
            }else {
                val = input;
            }
            return validator.accept(context, vr.expression(), val, expreParser, comparator);
        }catch (Exception e){
            return false;
        }
    }
}
