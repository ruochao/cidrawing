package com.mocircle.cidrawing.element;

import android.graphics.Matrix;
import android.graphics.Path;

public class PathElement extends BasePathElement {

    public PathElement() {
    }

    public void setElementPath(Path elementPath) {
        this.elementPath = elementPath;
        updateBoundingBox();
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        elementPath.transform(matrix);
    }

    @Override
    public Object clone() {
        PathElement element = new PathElement();
        cloneTo(element);
        return element;
    }
}
