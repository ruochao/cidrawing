package com.mocircle.cidrawing.utils;

import android.graphics.PointF;

import com.mocircle.cidrawing.BuildConfig;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ShapeUtilsTest {

    @Test
    public void testCalculateRotationDegree() {
        testCalculateRotationDegree(0, 0, 1, 1, 2, 2, 0);
        testCalculateRotationDegree(0, 0, 0, 1, 1, 1, -45);
        testCalculateRotationDegree(0, 0, 1, 0, 1, 1, 45);
        testCalculateRotationDegree(0, 0, 0, 1, 1, 0, -90);
        testCalculateRotationDegree(0, 0, 1, 0, 0, 1, 90);
        testCalculateRotationDegree(1, 1, 0, 2, 2, 1, -135);
        testCalculateRotationDegree(1, 1, 0, 2, 2, 0, -180);
        testCalculateRotationDegree(1, 1, 2, 1, 0, 1, 180);
        testCalculateRotationDegree(1, 1, 1, 0, 0, 1, -90);
    }

    private void testCalculateRotationDegree(float... values) {
        PointF center = new PointF(values[0], values[1]);
        PointF p1 = new PointF(values[2], values[3]);
        PointF p2 = new PointF(values[4], values[5]);
        float degree = ShapeUtils.calculateRotationDegree(center, p1, p2);
        Assert.assertEquals(values[6], degree);
    }

}
