package com.mocircle.cidrawing.operation;

import android.graphics.Matrix;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.utils.MatrixUtils;

public class DisplayTransformOperation extends AbstractOperation {

    protected DrawElement element;
    protected Matrix deltaMatrix;

    public DisplayTransformOperation(DrawElement element, Matrix deltaMatrix) {
        this.element = element;
        this.deltaMatrix = deltaMatrix;
    }

    @Override
    public boolean isExecutable() {
        return element != null;
    }

    @Override
    public boolean doOperation() {
        element.getDisplayMatrix().postConcat(deltaMatrix);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        element.getDisplayMatrix().postConcat(MatrixUtils.getInvertMatrix(deltaMatrix));
        drawingBoard.getDrawingView().notifyViewUpdated();
    }
}
