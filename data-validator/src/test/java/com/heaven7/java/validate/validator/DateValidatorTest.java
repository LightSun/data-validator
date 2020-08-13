package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.anno.ValidateDate;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorTest {

    DateValidator validator = new DateValidator();
    DateContext0 context = new DateContext0();

    @Test
    public void testString() throws Exception{
        Field field = Data.class.getDeclaredField("d1");
        field.setAccessible(true);
        ValidateDate anno = field.getAnnotation(ValidateDate.class);
        Assert.assertFalse(validator.accept(context, "2000-01-01", anno));
        Assert.assertTrue(validator.accept(context, "2000-01-02", anno));
        Assert.assertTrue(validator.accept(context, "2000-01-03", anno));
        Assert.assertFalse(validator.accept(context, "2000-01-04", anno));
    }

    @Test
    public void testLong() throws Exception{
        Field field = Data.class.getDeclaredField("d2");
        field.setAccessible(true);
        ValidateDate anno = field.getAnnotation(ValidateDate.class);
        Assert.assertFalse(validator.accept(context, getDateTime("2000-01-01"), anno));
        Assert.assertTrue(validator.accept(context, getDateTime("2000-01-02"), anno));
        Assert.assertTrue(validator.accept(context, getDateTime("2000-01-03"), anno));
        Assert.assertFalse(validator.accept(context, getDateTime("2000-01-04"), anno));
    }

    private long getDateTime(String text){
        try {
            return new SimpleDateFormat(context.getDateTemplate()).parse(text).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Data{
        @ValidateDate(value = "dfgg", rangeExpre = "2000-01-02 <= x < 2000-01-04")
        private String d1;
        @ValidateDate(value = "dfgg", rangeExpre = "2000-01-02 <= x < 2000-01-04")
        private long d2;
    }

    private static class DateContext0 implements DateContext{
        @Override
        public String getDateTemplate() {
            return "yyyy-MM-dd";
        }
    }
}
