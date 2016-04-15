package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;

public class RectElement extends ShapeElement {

    protected float left;
    protected float top;
    protected float right;
    protected float bottom;

    public RectElement() {
    }

    @Override
    public void buildShapeFromVector(Vector2 vector) {
        RectF box = vector.getRect();
        left = box.left;
        top = box.top;
        right = box.right;
        bottom = box.bottom;
        super.buildShapeFromVector(vector);
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.addRect(left, top, right, bottom, Path.Direction.CW);
        return path;
    }


}
