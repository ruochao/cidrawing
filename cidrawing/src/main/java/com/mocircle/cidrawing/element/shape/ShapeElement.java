package com.mocircle.cidrawing.element.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BaseElement;
import com.mocircle.cidrawing.element.VectorElement;

public abstract class ShapeElement extends VectorElement {

    protected transient Path shapePath;

    public ShapeElement() {
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        retrieveAttributesFromVector(vector);
        shapePath = createShapePath();
        updateBoundingBox();
    }

    @Override
    public void drawElement(Canvas canvas) {
        if (shapePath != null) {
            canvas.drawPath(shapePath, paint);
        }
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        shapePath = createShapePath();
        shapePath.transform(dataMatrix);
    }

    @Override
    public void updateBoundingBox() {
        if (shapePath != null) {
            RectF box = new RectF();
            shapePath.computeBounds(box, true);
            setBoundingBox(box);
        }
    }

    @Override
    public RectF getOuterBoundingBox() {
        if (shapePath != null) {
            Path path = new Path(shapePath);
            path.transform(getDisplayMatrix());
            RectF box = new RectF();
            path.computeBounds(box, true);
            return box;
        }
        return new RectF();
    }

    protected abstract void retrieveAttributesFromVector(Vector2 vector);

    protected abstract Path createShapePath();

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof ShapeElement) {
            ShapeElement obj = (ShapeElement) element;
            if (shapePath != null) {
                obj.shapePath = new Path(shapePath);
            }
        }
    }
}
