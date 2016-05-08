package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;

public class OvalElement extends BoxShapeElement {

    @Override
    public Object clone() {
        OvalElement element = new OvalElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.addOval(shapeBox, Path.Direction.CW);
        return path;
    }

}
