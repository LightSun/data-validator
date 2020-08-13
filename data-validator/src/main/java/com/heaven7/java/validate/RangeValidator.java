package com.heaven7.java.validate;

public interface RangeValidator {

    /**
     * the range validator
     * @param context the context
     * @param expre the expression
     * @param val the value
     * @param parser the parser to parse left and right expression
     * @return true if validate success . false otherwise.
     */
    boolean accept(Object context, String expre, Object val, Parser parser);

    interface Parser{
        Object parse(Object context, String str) throws Exception;
    }
}
