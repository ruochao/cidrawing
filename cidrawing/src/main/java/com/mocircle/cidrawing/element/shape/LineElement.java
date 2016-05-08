package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;
import android.graphics.PointF;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BaseElement;

public class LineElement extends ShapeElement {

    protected PointF point1;
    protected PointF point2;

    public LineElement() {
    }

    @Override
    public Object clone() {
        LineElement element = new LineElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected void retrieveAttributesFromVector(Vector2 vector) {
        this.point1 = vector.getPoint1();
        this.point2 = vector.getPoint2();
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        return path;
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof LineElement) {
            LineElement obj = (LineElement) element;
            if (point1 != null) {
                obj.point1 = new PointF(point1.x, point1.y);
            }
            if (point2 != null) {
                obj.point2 = new PointF(point2.x, point2.y);
            }
        }
    }
}
