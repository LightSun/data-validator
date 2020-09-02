package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.DateContext;
import com.heaven7.java.validate.Item;
import com.heaven7.java.validate.ValidateManager;
import com.heaven7.java.validate.anno.Sort;
import com.heaven7.java.validate.anno.ValidateDate;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ValidateManagerTest {

    private final ValidateManager mVm = ValidateManager.newBuilder(new DateContext0())
            .withDefaultPlugins()
            .build();

    @Test
    public void test1(){
        Item item = mVm.validate(new Data());
        System.out.println(item);

        item = mVm.validateWithSort(new Data("2000-01-01", 0));
        Assert.assertTrue(item.msg.equals("string date"));

        List<Item> list= mVm.validateAllWithSort(new Data("2000-01-01", 0));
        Assert.assertTrue(list.get(0).msg.equals("string date"));
        Assert.assertTrue(list.get(1).msg.equals("long date"));
    }

    public static class Data{
        @Sort(2)
        @ValidateDate(value = "string date", expression = "2000-01-02 <= x < 2000-01-04")
        private String d1;
        @Sort(1)
        @ValidateDate(value = "long date", expression = "2000-01-02 <= x < 2000-01-04")
        private long d2;

        public Data(String d1, long d2) {
            this.d1 = d1;
            this.d2 = d2;
        }
        public Data(){}
    }

    private static class DateContext0 implements DateContext {
        @Override
        public String getDateTemplate() {
            return "yyyy-MM-dd";
        }
    }
}
