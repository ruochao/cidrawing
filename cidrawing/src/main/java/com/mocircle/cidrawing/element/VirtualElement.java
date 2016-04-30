package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import java.util.List;

/**
 * A invisible element which holds elements, any transformation will represent to sub elements. This
 * virtual element just references to sub elements in the drawing board.
 */
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

            // Before applying data matrix, it should transfer the extra display matrices to data
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
    public void moveTo(float locX, float locY) {
        super.moveTo(locX, locY);
        for (DrawElement element : elements) {
            element.moveTo(locX, locY);
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
