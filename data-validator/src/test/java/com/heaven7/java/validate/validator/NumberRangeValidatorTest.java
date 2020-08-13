package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.parser.NumberParser;
import org.junit.Assert;
import org.junit.Test;

public class NumberRangeValidatorTest {

    final NumberRangeValidator validator = new NumberRangeValidator();
    final NumberParser parser = new NumberParser();

    @Test
    public void testLessLess(){
        Assert.assertFalse(validator.accept(null, "6 < x < 8", 8, parser));
        Assert.assertFalse(validator.accept(null, "6 < x < 8", 6, parser));
        Assert.assertTrue(validator.accept(null, "6 < x < 8", 7, parser));
    }

    @Test
    public void testLessLess2(){
        Assert.assertFalse(validator.accept(null, "6 <= x <= 8", 5, parser));
        Assert.assertFalse(validator.accept(null, "6 <= x <= 8", 9, parser));
        Assert.assertTrue(validator.accept(null, "6 <= x <= 8", 8, parser));
        Assert.assertTrue(validator.accept(null, "6 <= x <= 8", 6, parser));
        Assert.assertTrue(validator.accept(null, "6 <= x <= 8", 7, parser));
    }

    @Test
    public void testLessLess3(){
        Assert.assertFalse(validator.accept(null, "6 <= x < 8", 5, parser));
        Assert.assertFalse(validator.accept(null, "6 <= x < 8", 8, parser));
        Assert.assertTrue(validator.accept(null, "6 <= x < 8", 6, parser));
        Assert.assertTrue(validator.accept(null, "6 <= x < 8", 7,parser));
    }

    @Test
    public void testLessLess4(){
        Assert.assertFalse(validator.accept(null, "6 < x <= 8", 9, parser));
        Assert.assertTrue(validator.accept(null, "6 < x <= 8", 8, parser));
        Assert.assertFalse(validator.accept(null, "6 < x <= 8", 6, parser));
        Assert.assertTrue(validator.accept(null, "6 < x <= 8", 7, parser));
    }

    @Test
    public void testLargerLarger(){
        Assert.assertFalse(validator.accept(null, "8 > x > 6", 8,parser));
        Assert.assertFalse(validator.accept(null, "8 > x > 6", 6,parser));
        Assert.assertTrue(validator.accept(null, "8 > x > 6", 7,parser));
    }

    @Test
    public void testLargerLarger2(){
        Assert.assertTrue(validator.accept(null, "8 >= x >= 6", 8,parser));
        Assert.assertTrue(validator.accept(null, "8 >= x >= 6", 6,parser));
        Assert.assertTrue(validator.accept(null, "8 >= x >= 6", 7,parser));

        Assert.assertFalse(validator.accept(null, "8 >= x >= 6", 5,parser));
        Assert.assertFalse(validator.accept(null, "8 >= x >= 6", 9,parser));
    }

    @Test
    public void testLargerLarger3(){
        Assert.assertTrue(validator.accept(null, "8 >= x > 6", 8,parser));
        Assert.assertFalse(validator.accept(null, "8 >= x > 6", 6,parser));
        Assert.assertTrue(validator.accept(null, "8 >= x > 6", 7,parser));
        Assert.assertFalse(validator.accept(null, "8 >= x > 6", 9,parser));
    }

    @Test
    public void testLargerLarger4(){
        Assert.assertFalse(validator.accept(null, "8 > x >= 6", 8,parser));
        Assert.assertTrue(validator.accept(null, "8 > x >= 6", 6,parser));
        Assert.assertTrue(validator.accept(null, "8 > x >= 6", 7,parser));
        Assert.assertFalse(validator.accept(null, "8 > x >= 6", 5,parser));
    }
}