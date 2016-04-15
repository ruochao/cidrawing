package com.mocircle.cidrawing.utils;

import android.graphics.PointF;
import android.graphics.RectF;

public class ShapeUtils {

    public static RectF createSquare(float centerX, float centerY, float radius) {
        return new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    //TODO: optimize deltaX/deltaY
    public static float calculateRotationDegree(PointF center, PointF p1, PointF p2) {
        double angle1 = Math.atan2(p1.x - center.x, center.y - p1.y);
        double angle2 = Math.atan2(p2.x - center.x, center.y - p2.y);
        return (float) Math.toDegrees(angle2 - angle1);
    }

}
