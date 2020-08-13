package com.heaven7.java.validate.parser;

import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.RangeValidator;

import java.text.SimpleDateFormat;

public class NumberParser implements RangeValidator.Parser {

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
        return false;
    }


    private static boolean isDigit(String str){
        for (char ch : str.toCharArray()){
            if(!Character.isDigit(ch) && ch != DOT){
                return false;
            }
        }
        return true;
    }

}
