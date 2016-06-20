package com.mocircle.cidrawing.operation;

import android.graphics.Matrix;

import com.mocircle.cidrawing.utils.MatrixUtils;

public class FlipOperation extends SingleSelectedElementOperation {

    public enum FlipType {
        Vertical,
        Horizontal
    }

    private FlipType flipType;
    private Matrix flipMatrix;

    public FlipOperation() {
    }

    public FlipOperation(FlipType flipType) {
        this.flipType = flipType;
    }

    public FlipType getFlipType() {
        return flipType;
    }

    public void setFlipType(FlipType flipType) {
        this.flipType = flipType;
    }

    @Override
    public boolean doOperation() {
        flipMatrix = getFlipMatrix();
        selectedElement.applyMatrixForData(flipMatrix);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        if (selectedElement != null) {
            Matrix inverseFlipMatrix = MatrixUtils.getInvertMatrix(flipMatrix);
            selectedElement.applyMatrixForData(inverseFlipMatrix);
            drawingBoard.getDrawingView().notifyViewUpdated();
        }
    }

    private Matrix getFlipMatrix() {
        Matrix matrix = new Matrix();
        if (flipType == FlipType.Horizontal) {
            matrix.postScale(-1, 1, selectedElement.getReferencePoint().x, selectedElement.getReferencePoint().y);
        } else if (flipType == FlipType.Vertical) {
            matrix.postScale(1, -1, selectedElement.getReferencePoint().x, selectedElement.getReferencePoint().y);
        }
        return matrix;
    }
}
