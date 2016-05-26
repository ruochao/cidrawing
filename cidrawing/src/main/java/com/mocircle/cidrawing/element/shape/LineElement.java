package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;

public class LineElement extends BoxShapeElement {

    public LineElement() {
    }

    @Override
    public Path getTouchableArea() {
        Path path = new Path(super.getTouchableArea());
        if (shapeVector != null) {
            // Add a noise line to make the path easy to touch
            final int NOISE_SIZE = 10;
            if (Math.abs(shapeVector.getValueX()) < Math.abs(shapeVector.getValueY())) {
                path.lineTo(shapeVector.getPoint2().x + NOISE_SIZE, shapeVector.getPoint2().y);
            } else {
                path.lineTo(shapeVector.getPoint2().x, shapeVector.getPoint2().y + NOISE_SIZE);
            }
        }
        return path;
    }

    @Override
    public Object clone() {
        LineElement element = new LineElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.moveTo(shapeVector.getPoint1().x, shapeVector.getPoint1().y);
        path.lineTo(shapeVector.getPoint2().x, shapeVector.getPoint2().y);
        return path;
    }

}
