package com.heaven7.java.validate;

import java.util.Comparator;

public final class Comparators {

    public static final Comparator<Number> NUMBER = new Comparator<Number>() {
        @Override
        public int compare(Number o1, Number o2) {
            if(o1 instanceof Double || o2 instanceof Double){
                return Double.compare(o1.doubleValue(), o2.doubleValue());
            }
            if(o1 instanceof Long || o2 instanceof Long){
                return Long.compare(o1.longValue(), o2.longValue());
            }
            if(o1 instanceof Float || o2 instanceof Float){
                return Float.compare(o1.floatValue(), o2.floatValue());
            }
            return Integer.compare(o1.intValue(), o2.intValue());
        }
    };
}
