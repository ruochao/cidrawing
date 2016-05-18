package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;

public class IsoscelesTriangleElement extends BoxShapeElement {

    @Override
    public Object clone() {
        IsoscelesTriangleElement element = new IsoscelesTriangleElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.moveTo(shapeBox.left, shapeBox.bottom);
        path.lineTo(shapeBox.centerX(), shapeBox.top);
        path.lineTo(shapeBox.right, shapeBox.bottom);
        path.lineTo(shapeBox.left, shapeBox.bottom);
        return path;
    }
}
