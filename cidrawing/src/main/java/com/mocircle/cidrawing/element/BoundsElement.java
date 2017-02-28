package com.mocircle.cidrawing.element;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * A kind of element which is surrounded by the bounds.
 */
public abstract class BoundsElement extends DrawElement {

    protected transient RectF originalBoundingBox;
    protected transient Path boundingPath;

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);
        boundingPath.transform(matrix);
    }

    @Override
    public void updateBoundingBox() {
        if (boundingPath != null) {
            RectF box = new RectF();
            boundingPath.computeBounds(box, true);
            setBoundingBox(box);
        }
    }

    @Override
    public RectF getOuterBoundingBox() {
        if (boundingPath != null) {
            Path path = new Path(boundingPath);
            path.transform(getDisplayMatrix());
            RectF box = new RectF();
            path.computeBounds(box, true);
            return box;
        }
        return new RectF();
    }

    @Override
    public Path getTouchableArea() {
        if (boundingPath != null) {
            return boundingPath;
        } else {
            return new Path();
        }
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof BoundsElement) {
            BoundsElement obj = (BoundsElement) element;
            if (originalBoundingBox != null) {
                obj.originalBoundingBox = new RectF(originalBoundingBox);
            }
            if (boundingPath != null) {
                obj.boundingPath = new Path(boundingPath);
            }
        }
    }

    protected void updateBoundingBoxWithDataMatrix() {
        boundingPath.transform(getDataMatrix());
        updateBoundingBox();
    }

    protected abstract void calculateBoundingBox();

}
