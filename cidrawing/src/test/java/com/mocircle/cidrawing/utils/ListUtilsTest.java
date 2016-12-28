package com.mocircle.cidrawing.utils;

import com.mocircle.cidrawing.BuildConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ListUtilsTest {

    @Test
    public void testShiftItemNormal() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItem(list, 2, 1);
        Assert.assertArrayEquals(new String[]{"A", "B", "D", "C", "E"}, list.toArray());

        ListUtils.shiftItem(list, 2, -1);
        Assert.assertArrayEquals(new String[]{"A", "D", "B", "C", "E"}, list.toArray());
    }

    @Test
    public void testShiftItemOverflow() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItem(list, 1, 10);
        Assert.assertArrayEquals(new String[]{"A", "C", "D", "E", "B"}, list.toArray());

        ListUtils.shiftItem(list, 3, -10);
        Assert.assertArrayEquals(new String[]{"E", "A", "C", "D", "B"}, list.toArray());
    }

    @Test
    public void testShiftItemsWithFixedDistanceNormal() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItemsWithFixedDistance(list, new int[]{0, 2}, 1);
        Assert.assertArrayEquals(new String[]{"B", "A", "D", "C", "E"}, list.toArray());

        ListUtils.shiftItemsWithFixedDistance(list, new int[]{1, 3}, -1);
        Assert.assertArrayEquals(new String[]{"A", "B", "C", "D", "E"}, list.toArray());
    }

    @Test
    public void testShiftItemsWithFixedDistanceOverflow() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItemsWithFixedDistance(list, new int[]{0, 2}, 10);
        Assert.assertArrayEquals(new String[]{"B", "D", "A", "E", "C"}, list.toArray());

        ListUtils.shiftItemsWithFixedDistance(list, new int[]{1, 3}, -10);
        Assert.assertArrayEquals(new String[]{"D", "B", "E", "A", "C"}, list.toArray());
    }

    @Test
    public void testShiftItemsWithFixedDistanceOverflowCorner() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItemsWithFixedDistance(list, new int[]{1, 4}, 10);
        Assert.assertArrayEquals(new String[]{"A", "B", "C", "D", "E"}, list.toArray());

        ListUtils.shiftItemsWithFixedDistance(list, new int[]{0, 2}, -10);
        Assert.assertArrayEquals(new String[]{"A", "B", "C", "D", "E"}, list.toArray());
    }

    @Test
    public void testShiftItemsAsMuchAsPossibleNormal() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItemsAsMuchAsPossible(list, new int[]{0, 2}, 1);
        Assert.assertArrayEquals(new String[]{"B", "A", "D", "C", "E"}, list.toArray());

        ListUtils.shiftItemsAsMuchAsPossible(list, new int[]{1, 3}, -1);
        Assert.assertArrayEquals(new String[]{"A", "B", "C", "D", "E"}, list.toArray());
    }

    @Test
    public void testShiftItemsAsMuchAsPossibleOverflow() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "E");
        ListUtils.shiftItemsAsMuchAsPossible(list, new int[]{0, 2}, 10);
        Assert.assertArrayEquals(new String[]{"B", "D", "E", "A", "C"}, list.toArray());

        ListUtils.shiftItemsAsMuchAsPossible(list, new int[]{1, 3}, -10);
        Assert.assertArrayEquals(new String[]{"D", "A", "B", "E", "C"}, list.toArray());
    }

}
