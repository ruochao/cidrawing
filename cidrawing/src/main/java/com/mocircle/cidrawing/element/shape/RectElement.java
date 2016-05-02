package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BaseElement;

public class RectElement extends ShapeElement {

    protected float left;
    protected float top;
    protected float right;
    protected float bottom;

    public RectElement() {
    }

    @Override
    public Object clone() {
        RectElement element = new RectElement();
        cloneTo(element);
        return element;
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        RectF box = vector.getRect();
        left = box.left;
        top = box.top;
        right = box.right;
        bottom = box.bottom;
        super.setupElementByVector(vector);
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.addRect(left, top, right, bottom, Path.Direction.CW);
        return path;
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof RectElement) {
            RectElement obj = (RectElement) element;
            obj.left = left;
            obj.top = top;
            obj.right = right;
            obj.bottom = bottom;
        }
    }
}
