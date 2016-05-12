package com.mocircle.cidrawing.operation;

import android.graphics.Matrix;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.utils.MatrixUtils;

public class DataTransformOperation extends AbstractOperation {

    private DrawElement element;
    private Matrix deltaMatrix;

    public DataTransformOperation(DrawElement element, Matrix deltaMatrix) {
        this.element = element;
        this.deltaMatrix = deltaMatrix;
    }

    @Override
    public boolean isExecutable() {
        return element != null;
    }

    @Override
    public boolean doOperation() {
        element.applyMatrixForData(deltaMatrix);
        element.updateBoundingBox();
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        element.applyMatrixForData(MatrixUtils.getInvertMatrix(deltaMatrix));
        element.updateBoundingBox();
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

}
