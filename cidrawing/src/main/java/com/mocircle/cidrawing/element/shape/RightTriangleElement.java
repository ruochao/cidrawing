package com.mocircle.cidrawing.element.shape;

import android.graphics.Path;

import com.mocircle.cidrawing.element.BaseElement;

public class RightTriangleElement extends BoxShapeElement {

    private boolean leftRightAngle;

    public RightTriangleElement() {
    }

    public boolean isLeftRightAngle() {
        return leftRightAngle;
    }

    public void setLeftRightAngle(boolean leftRightAngle) {
        this.leftRightAngle = leftRightAngle;
    }

    @Override
    public Object clone() {
        RightTriangleElement element = new RightTriangleElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected Path createShapePath() {
        Path path = new Path();
        path.moveTo(shapeBox.left, shapeBox.bottom);
        if (leftRightAngle) {
            path.lineTo(shapeBox.left, shapeBox.top);
        } else {
            path.lineTo(shapeBox.right, shapeBox.top);
        }
        path.lineTo(shapeBox.right, shapeBox.bottom);
        path.lineTo(shapeBox.left, shapeBox.bottom);
        return path;
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof RightTriangleElement) {
            RightTriangleElement obj = (RightTriangleElement) element;
            obj.leftRightAngle = leftRightAngle;
        }
    }
}
