package com.heaven7.java.validate;

/*public*/ abstract class Operator<T> {

    public boolean greater(Object context, T t1, T t2){
        return less(context, t2, t1);
    }
    public boolean greaterThan(Object context, T t1, T t2){
        return lessThan(context, t2, t1);
    }
    public abstract boolean less(Object context, T t1, T t2);    // <

    public abstract boolean lessThan(Object context, T t1, T t2); // <=
}
