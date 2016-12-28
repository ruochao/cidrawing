package com.mocircle.cidrawing.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListUtils {

    /**
     * Convert integer list to int array.
     *
     * @param intList integer list
     * @return int array
     */
    public static int[] toIntArray(List<Integer> intList) {
        int[] result = new int[intList.size()];
        int index = 0;
        for (Integer i : intList) {
            result[index++] = i;
        }
        return result;
    }

    /**
     * Shift object in the list
     *
     * @param list   list
     * @param index  object index
     * @param offset offset value can be positive or negative.
     * @return new index of the object
     */
    public static int shiftItem(List<?> list, int index, int offset) {
        if (offset == 0 || index < 0) {
            return 0;
        }
        if (offset > 0) {
            int end = index + offset + 1;
            if (end > list.size()) {
                end = list.size();
            }
            Collections.rotate(list.subList(index, end), -1);
            return end - 1;
        } else {
            int start = index + offset;
            if (start < 0) {
                start = 0;
            }
            Collections.rotate(list.subList(start, index + 1), 1);
            return start;
        }
    }

    /**
     * Shift object to the top of list
     *
     * @param list  list
     * @param index object index
     */
    public static void shiftItemToFront(List<?> list, int index) {
        if (index >= 0) {
            Collections.rotate(list.subList(index, list.size()), -1);
        }
    }

    /**
     * Shift object to the bottom of list
     *
     * @param list  list
     * @param index object index
     */
    public static void shiftItemToBack(List<?> list, int index) {
        if (index >= 0) {
            Collections.rotate(list.subList(0, index + 1), 1);
        }
    }

    /**
     * Shift objects in the list, objects will try to move to the furthest distance.
     * <pre>
     * e.g. ABCED -> Shift AC with offset=5 -> BEDAC
     * </pre>
     *
     * @param list       list
     * @param indexArray object index array
     * @param offset     offset value can be positive or negative
     */
    public static void shiftItemsAsMuchAsPossible(List<?> list, int[] indexArray, int offset) {
        Arrays.sort(indexArray);
        if (offset > 0) {
            int last = list.size();
            for (int i = indexArray.length - 1; i >= 0; i--) {
                last = shiftItem(list.subList(0, last), indexArray[i], offset);
            }
        } else {
            int last = -1;
            for (int i = 0; i < indexArray.length; i++) {
                int index = indexArray[i] - (last + 1);
                last = shiftItem(list.subList(last + 1, list.size()), index, offset);
            }
        }
    }

    /**
     * Shift objects in the list, objects will keep the distance between them.
     * <pre>
     * e.g. ABCED -> Shift AC with offset=5 -> BEADC
     * </pre>
     *
     * @param list       list
     * @param indexArray object index array
     * @param offset     offset value can be positive or negative
     */
    public static void shiftItemsWithFixedDistance(List<?> list, int[] indexArray, int offset) {
        Arrays.sort(indexArray);
        int unionOffset = computeUnionOffset(list, indexArray, offset);
        shiftItemsDirectly(list, indexArray, unionOffset);
    }

    /**
     * Shift object directly without any pre-processing
     *
     * @param list       list
     * @param indexArray object index array
     * @param offset     offset value can be positive or negative
     */
    private static void shiftItemsDirectly(List<?> list, int[] indexArray, int offset) {
        if (offset > 0) {
            for (int i = indexArray.length - 1; i >= 0; i--) {
                shiftItem(list, indexArray[i], offset);
            }
        } else {
            for (int i = 0; i < indexArray.length; i++) {
                shiftItem(list, indexArray[i], offset);
            }
        }
    }

    /**
     * Calculate a offset for all objects in list.which make items will not shift out of the list.
     *
     * @param list       list
     * @param indexArray object index array
     * @param offset     initial offset value
     * @return new offset value
     */
    private static int computeUnionOffset(List<?> list, int[] indexArray, int offset) {
        if (offset > 0) {
            int unionIndex = indexArray[indexArray.length - 1];
            if (unionIndex + offset < list.size()) {
                return offset;
            } else {
                return list.size() - 1 - unionIndex;
            }
        } else {
            int unionIndex = indexArray[0];
            if (unionIndex + offset >= 0) {
                return offset;
            } else {
                return -unionIndex;
            }
        }
    }

}
