package com.mocircle.cidrawing.mode;

import android.graphics.Matrix;
import android.view.MotionEvent;

import com.mocircle.cidrawing.command.DataTransformCommand;
import com.mocircle.cidrawing.utils.MatrixUtils;

public abstract class DataTransformMode extends AutoDetectedElementOperationMode {

    protected Matrix originalDataMatrix;

    public DataTransformMode() {
    }

    public DataTransformMode(boolean autoDetectMode) {
        super(autoDetectMode);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (element == null) {
            return result;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                originalDataMatrix = new Matrix(element.getDataMatrix());
                return true;
            case MotionEvent.ACTION_UP:
                Matrix deltaMatrix = MatrixUtils.getTransformationMatrix(originalDataMatrix, element.getDataMatrix());
                resetTransformation(deltaMatrix);
                commandManager.executeCommand(new DataTransformCommand(element, deltaMatrix));
                return true;
            case MotionEvent.ACTION_CANCEL:
                deltaMatrix = MatrixUtils.getTransformationMatrix(originalDataMatrix, element.getDataMatrix());
                resetTransformation(deltaMatrix);
                return true;
        }
        return result;
    }

    protected void resetTransformation(Matrix deltaMatrix) {
        element.applyMatrixForData(MatrixUtils.getInvertMatrix(deltaMatrix));
        element.updateBoundingBox();
    }

}
