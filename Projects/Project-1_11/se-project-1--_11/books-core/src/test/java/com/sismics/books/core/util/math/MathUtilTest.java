package com.sismics.books.core.util.math;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test math utilities.
 * 
 * @author bgamard
 */
public class MathUtilTest {

    @Test
    public void testRandomHexColor() throws Exception {
        String color = MathUtil.randomHexColor();
        Assert.assertEquals('#', color.charAt(0));
        Assert.assertEquals(7, color.length());
    }
}
