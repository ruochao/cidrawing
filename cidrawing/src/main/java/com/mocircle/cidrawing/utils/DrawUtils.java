package com.mocircle.cidrawing.utils;

import android.content.Context;
import android.view.ViewConfiguration;

public class DrawUtils {

    public static boolean isSingleTap(Context context, float x1, float y1, float x2, float y2) {
        int touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        final int deltaX = (int) (x1 - x2);
        final int deltaY = (int) (y1 - y2);
        int distance = (deltaX * deltaX) + (deltaY * deltaY);
        return distance <= touchSlop * touchSlop;
    }

}
