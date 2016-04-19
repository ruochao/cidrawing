package com.mocircle.cidrawing.element;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementGroup extends DrawElement {

    protected transient RectF initBoundingBox;
    protected transient Path boundingPath;
    protected List<DrawElement> elements;

    public ElementGroup() {
    }

    public ElementGroup(List<DrawElement> elements) {
        setElements(elements);
    }

    public List<DrawElement> getElements() {
        return elements;
    }

    public void setElements(List<DrawElement> elements) {
        this.elements = elements;
        updateBoundingBoxForFirstTime();
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        boundingPath = new Path();
        boundingPath.addRect(initBoundingBox, Path.Direction.CW);
        boundingPath.transform(dataMatrix);

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
    public void updateBoundingBox() {
        if (boundingPath != null) {
            RectF box = new RectF();
            boundingPath.computeBounds(box, true);
            setBoundingBox(box);
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

    @Override
    public void resize(float sx, float sy, float px, float py) {
        super.resize(sx, sy, px, py);
    }

    @Override
    public void resizeTo(float sx, float sy, float px, float py) {
        super.resizeTo(sx, sy, px, py);
    }

    @Override
    public void skew(float kx, float ky, float px, float py) {
        super.skew(kx, ky, px, py);
    }

    @Override
    public void skewTo(float kx, float ky, float px, float py) {
        super.skewTo(kx, ky, px, py);
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof ElementGroup) {
            ElementGroup obj = (ElementGroup) element;
            obj.initBoundingBox = new RectF(initBoundingBox);
            obj.boundingPath = new Path(boundingPath);
            if (elements != null) {
                obj.elements = new ArrayList<>();
                for (DrawElement e : elements) {
                    obj.elements.add((DrawElement) e.clone());
                }
            }
        }
    }

    private void updateBoundingBoxForFirstTime() {
        RectF box = new RectF();
        for (DrawElement element : elements) {
            box.union(element.getOuterBoundingBox());
        }

        initBoundingBox = new RectF(box);
        boundingPath = new Path();
        boundingPath.addRect(box, Path.Direction.CW);
        updateBoundingBox();
    }

}
