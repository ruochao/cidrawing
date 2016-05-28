package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

public abstract class BasePathElement extends DrawElement {

    protected Path elementPath;

    public BasePathElement() {
    }

    public Path getElementPath() {
        return elementPath;
    }

    public Path getActualElementPath() {
        Path path = new Path(elementPath);
        path.transform(displayMatrix);
        return path;
    }

    @Override
    public Path getTouchableArea() {
        if (elementPath != null) {
            return elementPath;
        } else {
            return new Path();
        }
    }

    @Override
    public void updateBoundingBox() {
        if (elementPath != null) {
            RectF box = new RectF();
            elementPath.computeBounds(box, true);
            setBoundingBox(box);
        }
    }

    @Override
    public RectF getOuterBoundingBox() {
        if (elementPath != null) {
            Path path = new Path(elementPath);
            path.transform(getDisplayMatrix());
            RectF box = new RectF();
            path.computeBounds(box, true);
            return box;
        }
        return new RectF();
    }

    @Override
    public void drawElement(Canvas canvas) {
        if (elementPath != null) {
            canvas.drawPath(elementPath, paint);
        }
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof BasePathElement) {
            BasePathElement obj = (BasePathElement) element;
            if (elementPath != null) {
                obj.elementPath = new Path(elementPath);
            }
        }
    }
}
