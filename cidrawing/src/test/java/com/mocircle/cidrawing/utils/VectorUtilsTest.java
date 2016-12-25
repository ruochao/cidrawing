package com.mocircle.cidrawing.utils;

import com.mocircle.cidrawing.BuildConfig;
import com.mocircle.cidrawing.core.Vector2;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class VectorUtilsTest {

    @Test
    public void testShiftVectorLargerFirst() {
        testShiftVectorAsSquare(false, 0, 0, 1, 2, 2, 2);
        testShiftVectorAsSquare(false, 0, 0, 2, 1, 2, 2);

        testShiftVectorAsSquare(false, 0, 0, -1, 2, -2, 2);
        testShiftVectorAsSquare(false, 0, 0, -2, 1, -2, 2);

        testShiftVectorAsSquare(false, 0, 0, 1, -2, 2, -2);
        testShiftVectorAsSquare(false, 0, 0, 2, -1, 2, -2);

        testShiftVectorAsSquare(false, 0, 0, -1, -2, -2, -2);
        testShiftVectorAsSquare(false, 0, 0, -2, -1, -2, -2);
    }

    @Test
    public void testShiftVectorSmallerFirst() {
        testShiftVectorAsSquare(true, 0, 0, 1, 2, 1, 1);
        testShiftVectorAsSquare(true, 0, 0, 2, 1, 1, 1);

        testShiftVectorAsSquare(true, 0, 0, -1, 2, -1, 1);
        testShiftVectorAsSquare(true, 0, 0, -2, 1, -1, 1);

        testShiftVectorAsSquare(true, 0, 0, 1, -2, 1, -1);
        testShiftVectorAsSquare(true, 0, 0, 2, -1, 1, -1);

        testShiftVectorAsSquare(true, 0, 0, -1, -2, -1, -1);
        testShiftVectorAsSquare(true, 0, 0, -2, -1, -1, -1);
    }

    private void testShiftVectorAsSquare(boolean smallerFirst, float... values) {
        Vector2 vector = new Vector2(values[0], values[1], values[2], values[3]);
        VectorUtils.shiftVectorAsSquare(vector, smallerFirst);
        Assert.assertEquals(values[4], vector.getPoint2().x);
        Assert.assertEquals(values[5], vector.getPoint2().y);
    }

}
