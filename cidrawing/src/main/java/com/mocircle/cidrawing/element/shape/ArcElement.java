package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;

public class ArcElement extends BoxShapeElement {

    @Override
    public Object clone() {
        ArcElement element = new ArcElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.moveTo(shapeVector.getPoint1().x, shapeVector.getPoint1().y);
        path.quadTo(shapeVector.getPoint1().x, shapeVector.getPoint2().y, shapeVector.getPoint2().x, shapeVector.getPoint2().y);
        return path;
    }
}
