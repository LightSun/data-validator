package com.heaven7.java.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * the value reader.
 */
public interface ValueReader {

    /**
     * read value from owner
     * @param context the context
     * @param owner the owner
     * @param f the field
     * @return the value
     * @throws Exception if occurs.
     */
    Object read(Object context, Object owner, Field f) throws Exception;

    /**
     * default value reader
     */
    ValueReader DEFAULT = new ValueReader() {
        @Override
        public Object read(Object context,Object owner, Field f) throws Exception{
            return f.get(owner);
        }
    };
    /**
     * android-view text reader. such as TextView/EditText.
     */
    ValueReader ANDROID_VIEW_TEXT_READER = new ValueReader() {
        @Override
        public Object read(Object context,Object owner, Field f) throws Exception{
            owner = f.get(owner);
            Method m = owner.getClass().getMethod("getText");
            return m.invoke(owner).toString();
        }
    };
}
