package com.mocircle.cidrawing.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * A utility class for drawing.
 */
public final class DrawUtils {

    private DrawUtils() {
    }

    /**
     * Checks if touch event is treat as single tap
     *
     * @param context android context
     * @param downX   axis x of touch down
     * @param downY   axis y of touch down
     * @param upEvent MotionEvent for touch up
     * @return true if it's single tap, otherwise false
     */
    public static boolean isSingleTap(Context context, float downX, float downY, MotionEvent upEvent) {
        return isSingleTap(context, upEvent.getDownTime(), upEvent.getEventTime(), downX, downY, upEvent.getX(), upEvent.getY());
    }

    /**
     * Checks if touch event is treat as single tap
     *
     * @param context  android context
     * @param downTime the time when touch down
     * @param upTime   the time when touch up
     * @param downX    axis x of touch down
     * @param downY    axis y of touch down
     * @param upX      axis  x of touch up
     * @param upY      axis  y of touch up
     * @return true if it's single tap, otherwise false
     */
    public static boolean isSingleTap(Context context, long downTime, long upTime, float downX, float downY, float upX, float upY) {
        if (upTime - downTime > ViewConfiguration.getTapTimeout()) {
            return false;
        }
        int touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        final int deltaX = (int) (downX - upX);
        final int deltaY = (int) (downY - upY);
        int distance = (deltaX * deltaX) + (deltaY * deltaY);
        return distance <= touchSlop * touchSlop;
    }

    /**
     * Create a rectangle based on touch point as center point and touch slop as half side.
     *
     * @param context android context
     * @param x       axis x of touch point
     * @param y       axis y of touch point
     * @return rectangle for touch point
     */
    public static Rect createTouchSquare(Context context, int x, int y) {
        int touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        return new Rect(x - touchSlop, y - touchSlop, x + touchSlop, y + touchSlop);
    }
}
