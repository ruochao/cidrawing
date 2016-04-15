package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;
import android.graphics.PointF;

import com.mocircle.cidrawing.core.Vector2;

public class LineElement extends ShapeElement {

    protected PointF point1;
    protected PointF point2;

    public LineElement() {
    }

    @Override
    public void buildShapeFromVector(Vector2 vector) {
        this.point1 = vector.getPoint1();
        this.point2 = vector.getPoint2();
        super.buildShapeFromVector(vector);
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        return path;
    }


}
