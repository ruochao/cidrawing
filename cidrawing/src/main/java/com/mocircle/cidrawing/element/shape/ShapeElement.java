package com.mocircle.cidrawing.element.shape;

import android.graphics.Matrix;
import android.graphics.Path;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BasePathElement;
import com.mocircle.cidrawing.element.behavior.CreateByVector;

public abstract class ShapeElement extends BasePathElement implements CreateByVector {

    public ShapeElement() {
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        retrieveAttributesFromVector(vector);
        elementPath = createShapePath();
        updateBoundingBox();
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        elementPath = createShapePath();
        elementPath.transform(dataMatrix);
    }

    protected abstract void retrieveAttributesFromVector(Vector2 vector);

    protected abstract Path createShapePath();
}
