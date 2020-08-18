package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.RangeValidator;

import java.util.Comparator;

//a </<=/>/>= x </<=/>/>= b
public class SimpleRangeValidator implements RangeValidator {

    private static final char CH_LESS = '<';
    private static final char CH_LARGER = '>';
    private static final char CH_EQ = '=';

    @Override
    public boolean accept(Object context, String expre, Object val, Parser parser, Comparator com) {
        int i = expre.indexOf('x');
        final String s1 = expre.substring(0, i).trim();
        final String s2 = expre.substring(i + 1).trim();
        final boolean s1_has_eq = s1.charAt(s1.length() -1) == CH_EQ;
        final boolean s2_has_eq = s2.charAt(1) == CH_EQ;

        final String str_left = s1_has_eq ? s1.substring(0, s1.length() - 2).trim() : s1.substring(0, s1.length() - 1).trim();
        final String str_right = s2_has_eq ? s2.substring(2).trim() : s2.substring(1).trim();

        final char ch1 = s1.charAt(s1_has_eq ? s1.length() - 2 : s1.length() - 1);
        final char ch2 = s2.charAt(0);
        final boolean lef_less = ch1 == CH_LESS;
        final boolean lef_larger = ch1 == CH_LARGER;
        final boolean right_less = ch2 == CH_LESS;
        final boolean right_larger = ch2 == CH_LARGER;

        Object left, right;
        try {
            left =  parser.parse(context, str_left);
            right = parser.parse(context, str_right);
        }catch (Exception e){
            return false;
        }
        // 1 < x or 1 <= x
        if(lef_less) {
            if (s1_has_eq) {
                if (com.compare(val, left) < 0) {
                    return false;
                }
            } else {
                if (com.compare(val, left) <= 0) {
                    return false;
                }
            }
        }else if(lef_larger){
            if (s1_has_eq) {
                if (com.compare(val, left) > 0) {
                    return false;
                }
            } else {
                if (com.compare(val, left) >= 0) {
                    return false;
                }
            }
        }
        // x < 2 or x <= 2
        if(right_less){
            if(s2_has_eq){
                if(com.compare(val, right) > 0){
                    return false;
                }
            }else {
                if(com.compare(val, right) >= 0){
                    return false;
                }
            }
        }else if(right_larger){
            if(s2_has_eq){
                if(com.compare(val, right) < 0){
                    return false;
                }
            }else {
                if(com.compare(val, right) <= 0){
                    return false;
                }
            }
        }
        return true;
    }
}
