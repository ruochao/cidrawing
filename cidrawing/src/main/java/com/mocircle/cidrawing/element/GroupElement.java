package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.List;

public class GroupElement extends ElementGroup {

    public GroupElement() {
        super();
    }

    public GroupElement(List<DrawElement> elements) {
        super(elements);
    }

    @Override
    public Object clone() {
        GroupElement element = new GroupElement();
        cloneTo(element);
        return element;
    }

    @Override
    public void drawElement(Canvas canvas) {
        for (DrawElement element : elements) {
            canvas.save();
            Matrix originalDisplay = new Matrix(element.getDisplayMatrix());
            originalDisplay.postConcat(getInvertedDisplayMatrix());
            canvas.concat(originalDisplay);
            element.drawElement(canvas);
            canvas.restore();
        }
    }

}
