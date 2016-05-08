package com.mocircle.cidrawing.element.shape;

import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BaseElement;

/**
 * A kind of shape which is able to be drawn correctly with a given box.
 */
public abstract class BoxShapeElement extends ShapeElement {

    protected RectF shapeBox;

    @Override
    protected void retrieveAttributesFromVector(Vector2 vector) {
        shapeBox = vector.getRect();
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof BoxShapeElement) {
            BoxShapeElement obj = (BoxShapeElement) element;
            obj.shapeBox = new RectF(shapeBox);
        }
    }
}
