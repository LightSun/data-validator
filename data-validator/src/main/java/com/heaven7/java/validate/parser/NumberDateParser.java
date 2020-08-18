package com.heaven7.java.validate.parser;

import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.RangeValidator;

import java.text.SimpleDateFormat;

/**
 * the parser which can par
 */
public class NumberDateParser implements RangeValidator.Parser {

    private static final char DOT = '.';
    @Override
    public Object parse(Object context, String str) throws Exception {
        if(isDigit(str)){
            if(str.contains(".")){
                return Double.valueOf(str);
            }
            return Long.valueOf(str);
        }else if(context instanceof DateContext){
            return new SimpleDateFormat(((DateContext) context).getDateTemplate()).parse(str).getTime();
        }
        return null;
    }

    private static boolean isDigit(String str){
        for (char ch : str.toCharArray()){
            if(ch != DOT && !Character.isDigit(ch)){
                return false;
            }
        }
        return true;
    }

}
