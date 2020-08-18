package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.RangeValidator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//a </<=/>/>= x </<=/>/>= b
public class SimpleRangeValidator implements RangeValidator {

    private static final Pattern PAT = Pattern.compile("([<[>]])=?");
    private static final Map<String, Operator> sOperators = new HashMap<>(6);

    static {
        sOperators.put("<", new LessOperator());
        sOperators.put("<=", new LessThanOperator());
        sOperators.put(">", new GreaterOperator());
        sOperators.put(">=", new GreaterThanOperator());
    }

    private static List<Item> findSymbols(String in){
        List<Item> items = new ArrayList<>();
        Matcher matcher = PAT.matcher(in);
        while (matcher.find()){
            items.add(new Item(matcher.group(), matcher.start(), matcher.end()));
        }
        Item last = null;
        Item cur, next;
        String left, right;
        for (int i = 0, size = items.size() ; i < size; i ++){
            cur = items.get(i);
            next =  i != size - 1 ? items.get(i + 1) : null;
            left = in.substring(last != null ? last.end + 1 : 0, cur.start).trim();
            right = in.substring(cur.end + 1, next != null ? next.start : in.length()).trim();

            cur.leftExpre = left;
            cur.rightExpre = right;
            //System.out.println(cur.symbol + " : "  + String.format("left = %s ,right = %s", left, right));
            last = cur;
        }
        return items;
    }

    @Override
    public boolean accept(Object context, String expre, Object val, Parser parser, Comparator com) throws Exception{
        List<Item> items = findSymbols(expre);
        if(items.size() == 0){
            return false;
        }
        //a < x <= c
        //a < b > c <= d >= e
        for (Item item: items){
            Object left = item.leftExpre.equals("x") ? val : parser.parse(context, item.leftExpre);
            if(left == null){
                throw new IllegalStateException("can't parse expre = " + item.leftExpre + " ,parser = " + parser.getClass().getName());
            }
            Object right = item.rightExpre.equals("x") ? val : parser.parse(context, item.rightExpre);
            if(right == null){
                throw new IllegalStateException("can't parse expre = " + item.rightExpre + " ,parser = " + parser.getClass().getName());
            }
            Operator operator = sOperators.get(item.symbol);
            if(operator == null){
                throw new IllegalStateException("can't find operator = " + item.symbol);
            }
            if(!operator.compare(com, left, right)){
                return false;
            }
        }
        return true;
    }
    private static class Item{
        final String symbol;
        final int start;
        final int end;

        String leftExpre;
        String rightExpre;

        public Item(String symbol, int start, int end) {
            this.symbol = symbol;
            this.start = start;
            this.end = end;
        }
    }

    private interface Operator{
        boolean compare(Comparator com, Object v1, Object v2);
    }
    private static class LessOperator implements Operator{
        @Override
        public boolean compare(Comparator com, Object v1, Object v2) {
            return com.compare(v1, v2) < 0;
        }
    }
    private static class LessThanOperator implements Operator{
        @Override
        public boolean compare(Comparator com, Object v1, Object v2) {
            return com.compare(v1, v2) <= 0;
        }
    }
    private static class GreaterOperator implements Operator{
        @Override
        public boolean compare(Comparator com, Object v1, Object v2) {
            return com.compare(v1, v2) > 0;
        }
    }
    private static class GreaterThanOperator implements Operator{
        @Override
        public boolean compare(Comparator com, Object v1, Object v2) {
            return com.compare(v1, v2) >= 0;
        }
    }
}
