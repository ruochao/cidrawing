package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.List;

public class GroupElement extends DrawElement {

    private List<DrawElement> elements;

    public GroupElement(List<DrawElement> elements) {
        this.elements = elements;
        updateBoundingBox();
    }

    @Override
    public void updateBoundingBox() {
        RectF box = new RectF();
        for (DrawElement element : elements) {
            box.union(element.getOuterBoundingBox());
        }
        setBoundingBox(box);
    }

    @Override
    public void drawElement(Canvas canvas) {

    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
        for (DrawElement element : elements) {
            element.move(x, y);
        }
    }
}
