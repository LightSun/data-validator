package com.heaven7.java.validate;


import java.util.Comparator;

public interface RangeValidator {

    /**
     * the range validator
     * @param context the context
     * @param expre the expression
     * @param val the value
     * @param parser the parser to parse left and right expression
     * @param com the comparator
     * @return true if validate success . false otherwise.
     */
    boolean accept(Object context, String expre, Object val, Parser parser, Comparator com) throws Exception;

    interface Parser{
        /**
         * parse the expression to object
         * @param context the context
         * @param str the expre
         * @return the parsed object or null if known expression.
         * @throws Exception if occurs
         */
        Object parse(Object context, String str) throws Exception;
    }
}
