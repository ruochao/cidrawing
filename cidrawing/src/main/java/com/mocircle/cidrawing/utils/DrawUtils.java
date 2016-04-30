package com.mocircle.cidrawing.utils;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * A utility class for drawing.
 */
public final class DrawUtils {

    private DrawUtils() {
    }

    /**
     * Checks if two points are close enough to be treated as a single tap.
     *
     * @param context android context
     * @param x1      x of point 1
     * @param y1      y of point 1
     * @param x2      x of point 2
     * @param y2      y of point 2
     * @return true if it's single tap, otherwise false
     */
    public static boolean isSingleTap(Context context, float x1, float y1, float x2, float y2) {
        int touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        final int deltaX = (int) (x1 - x2);
        final int deltaY = (int) (y1 - y2);
        int distance = (deltaX * deltaX) + (deltaY * deltaY);
        return distance <= touchSlop * touchSlop;
    }

}
