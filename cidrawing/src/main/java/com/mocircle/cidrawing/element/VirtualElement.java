package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.List;

public class VirtualElement extends ElementGroup {

    public VirtualElement() {
        super();
    }

    public VirtualElement(List<DrawElement> elements) {
        super(elements);
    }

    @Override
    public Object clone() {
        VirtualElement element = new VirtualElement();
        cloneTo(element);
        return element;
    }

    @Override
    public void drawElement(Canvas canvas) {
        // Draw nothing but debug info
        if (configManager.isDebugMode()) {
            canvas.drawRect(boundingBox, debugPaintForArea);
        }
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        for (DrawElement element : elements) {

            // Before applying data matrix, it should restore the extra display matrices
            // which including element's display matrix and parent's display matrix.

            Matrix parentDisplay = new Matrix(getDisplayMatrix());
            Matrix parentInvertDisplay = new Matrix(getInvertedDisplayMatrix());
            Matrix originalDisplay = new Matrix(element.getDisplayMatrix());
            originalDisplay.postConcat(parentInvertDisplay);
            Matrix originalInvertDisplay = new Matrix();
            originalDisplay.invert(originalInvertDisplay);

            element.getDisplayMatrix().postConcat(parentInvertDisplay);
            element.applyDisplayMatrixToData();
            element.applyMatrixForData(matrix);
            element.applyMatrixForData(originalInvertDisplay);
            element.getDisplayMatrix().postConcat(originalDisplay);
            element.getDisplayMatrix().postConcat(parentDisplay);
            element.updateBoundingBox();
        }
    }

    @Override
    public RectF getOuterBoundingBox() {
        RectF box = new RectF();
        for (DrawElement element : elements) {
            box.union(element.getOuterBoundingBox());
        }
        return box;
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
        for (DrawElement element : elements) {
            element.move(x, y);
        }
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        for (DrawElement element : elements) {
            element.moveTo(x, y);
        }
    }

    @Override
    public void rotate(float degree, float px, float py) {
        super.rotate(degree, px, py);
        for (DrawElement element : elements) {
            element.rotate(degree, px, py);
        }
    }

    @Override
    public void rotateTo(float degree, float px, float py) {
        super.rotateTo(degree, px, py);
        for (DrawElement element : elements) {
            element.rotateTo(degree, px, py);
        }
    }

}
