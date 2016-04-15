package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.List;

public class VirtualElement extends DrawElement {

    private List<DrawElement> elements;

    public VirtualElement(List<DrawElement> elements) {
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
        // Draw nothing
    }

    @Override
    public boolean isResizingEnabled() {
        return false;
    }

    @Override
    public boolean isSkewEnabled() {
        return false;
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
        for (DrawElement element : elements) {
            element.move(x, y);
        }
    }

    @Override
    public void rotate(float degree, float px, float py) {
        super.rotate(degree, px, py);
        for (DrawElement element : elements) {
            element.rotate(degree, px, py);
        }
    }

}
