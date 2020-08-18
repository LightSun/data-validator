package com.heaven7.java.validate.validator;

import com.heaven7.java.validate.Comparators;
import com.heaven7.java.validate.parser.NumberDateParser;
import org.junit.Assert;
import org.junit.Test;

public class NumberRangeValidatorTest {

    final SimpleRangeValidator validator = new SimpleRangeValidator();
    final NumberDateParser parser = new NumberDateParser();

    @Test
    public void testLessLess() throws Exception {
        Assert.assertFalse(validator.accept(null, "6 < x < 8", 8, parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "6 < x < 8", 6, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 < x < 8", 7, parser, Comparators.NUMBER));
    }

    @Test
    public void testLessLess2() throws Exception {
        Assert.assertFalse(validator.accept(null, "6 <= x <= 8", 5, parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "6 <= x <= 8", 9, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 <= x <= 8", 8, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 <= x <= 8", 6, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 <= x <= 8", 7, parser, Comparators.NUMBER));
    }

    @Test
    public void testLessLess3() throws Exception {
        Assert.assertFalse(validator.accept(null, "6 <= x < 8", 5, parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "6 <= x < 8", 8, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 <= x < 8", 6, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 <= x < 8", 7,parser, Comparators.NUMBER));
    }

    @Test
    public void testLessLess4() throws Exception {
        Assert.assertFalse(validator.accept(null, "6 < x <= 8", 9, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 < x <= 8", 8, parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "6 < x <= 8", 6, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "6 < x <= 8", 7, parser, Comparators.NUMBER));
    }

    @Test
    public void testLargerLarger() throws Exception {
        Assert.assertFalse(validator.accept(null, "8 > x > 6", 8,parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 > x > 6", 6,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 > x > 6", 7,parser, Comparators.NUMBER));
    }

    @Test
    public void testLargerLarger2() throws Exception {
        Assert.assertTrue(validator.accept(null, "8 >= x >= 6", 8,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 >= x >= 6", 6,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 >= x >= 6", 7,parser, Comparators.NUMBER));

        Assert.assertFalse(validator.accept(null, "8 >= x >= 6", 5,parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 >= x >= 6", 9,parser, Comparators.NUMBER));
    }

    @Test
    public void testLargerLarger3() throws Exception {
        Assert.assertTrue(validator.accept(null, "8 >= x > 6", 8,parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 >= x > 6", 6,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 >= x > 6", 7,parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 >= x > 6", 9,parser, Comparators.NUMBER));
    }

    @Test
    public void testLargerLarger4() throws Exception {
        Assert.assertFalse(validator.accept(null, "8 > x >= 6", 8,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 > x >= 6", 6,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 > x >= 6", 7,parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 > x >= 6", 5,parser, Comparators.NUMBER));
    }

    @Test
    public void testGreater() throws Exception {
        Assert.assertFalse(validator.accept(null, "8 > x ", 8,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 > x ", 7,parser, Comparators.NUMBER));

        Assert.assertTrue(validator.accept(null, "x > 8", 9,parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "x > 8", 8,parser, Comparators.NUMBER));
    }

    @Test
    public void testLess() throws Exception {
        Assert.assertFalse(validator.accept(null, "8 < x ", 8,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 < x ", 9,parser, Comparators.NUMBER));

        Assert.assertFalse(validator.accept(null, "x < 8", 8,parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "x < 8", 7,parser, Comparators.NUMBER));
    }

    @Test
    public void testLessThan() throws Exception {
        Assert.assertTrue(validator.accept(null, "8 <= x ", 8, parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 <= x ", 7.999999, parser, Comparators.NUMBER));
    }

    @Test
    public void testGreaterThan() throws Exception {
        Assert.assertTrue(validator.accept(null, "8 >= x ", 8, parser, Comparators.NUMBER));
        Assert.assertTrue(validator.accept(null, "8 >= x ", 7.999999, parser, Comparators.NUMBER));
        Assert.assertFalse(validator.accept(null, "8 >= x ", 8.0001, parser, Comparators.NUMBER));
    }
}
