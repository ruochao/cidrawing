package com.mocircle.cidrawing.core;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Vector defines in 2D.
 */
public class Vector2 {

    private PointF point1;
    private PointF point2;

    public Vector2() {
    }

    public Vector2(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Vector2(float x1, float y1, float x2, float y2) {
        point1 = new PointF(x1, y1);
        point2 = new PointF(x2, y2);
    }

    public PointF getPoint1() {
        return point1;
    }

    public void setPoint1(PointF point1) {
        this.point1 = point1;
    }

    public PointF getPoint2() {
        return point2;
    }

    public void setPoint2(PointF point2) {
        this.point2 = point2;
    }

    public float getValueX() {
        return point2.x - point1.x;
    }

    public float getValueY() {
        return point2.y - point1.y;
    }

    public void offset(float dx, float dy) {
        point1.offset(dx, dy);
        point2.offset(dx, dy);
    }

    public RectF getRect() {
        RectF rect = new RectF(point1.x, point1.y, point2.x, point2.y);
        rect.sort();
        return rect;
    }


}
