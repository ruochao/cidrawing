package com.mocircle.cidrawing.utils;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * A utility class about shape.
 */
public final class ShapeUtils {

    private ShapeUtils() {
    }

    /**
     * Creates square based on circle.
     *
     * @param centerX x of center point
     * @param centerY y of center point
     * @param radius  radius
     * @return square object
     */
    public static RectF createSquare(float centerX, float centerY, float radius) {
        return new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    /**
     * Gets the angle from P(center, p1) to P(center, p2), the value range is [-180, 180].
     *
     * @param center center point
     * @param p1     point 1
     * @param p2     point 2
     * @return angle
     */
    public static float calculateRotationDegree(PointF center, PointF p1, PointF p2) {
        double angle1 = Math.atan2(p1.y - center.y, p1.x - center.x);
        double angle2 = Math.atan2(p2.y - center.y, p2.x - center.x);
        float angle = (float) Math.toDegrees(angle2 - angle1);
        if (angle > 180) {
            angle -= 360;
        } else if (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Creates a simple region from the given path.
     *
     * @param path given path
     * @return region object
     */
    public static Region createRegionFromPath(Path path) {
        Region region = new Region();
        if (path != null) {
            RectF box = new RectF();
            path.computeBounds(box, true);
            region.setPath(path, new Region((int) box.left, (int) box.top, (int) box.right, (int) box.bottom));
        }
        return region;
    }

}
