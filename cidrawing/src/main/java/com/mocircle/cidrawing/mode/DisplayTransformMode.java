package com.mocircle.cidrawing.mode;

import android.graphics.Matrix;
import android.view.MotionEvent;

import com.mocircle.cidrawing.command.DisplayTransformCommand;
import com.mocircle.cidrawing.utils.MatrixUtils;

public abstract class DisplayTransformMode extends AutoDetectedElementOperationMode {

    protected Matrix originalDisplayMatrix;

    public DisplayTransformMode() {
    }

    public DisplayTransformMode(boolean autoDetectMode) {
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
                originalDisplayMatrix = new Matrix(element.getDisplayMatrix());
                return true;
            case MotionEvent.ACTION_UP:
                Matrix deltaMatrix = MatrixUtils.getTransformationMatrix(originalDisplayMatrix, element.getDisplayMatrix());
                resetTransformation(deltaMatrix);
                commandManager.executeCommand(new DisplayTransformCommand(element, deltaMatrix));
                return true;
            case MotionEvent.ACTION_CANCEL:
                deltaMatrix = MatrixUtils.getTransformationMatrix(originalDisplayMatrix, element.getDisplayMatrix());
                resetTransformation(deltaMatrix);
                return true;
        }
        return result;
    }

    protected void resetTransformation(Matrix deltaMatrix) {
        element.getDisplayMatrix().postConcat(MatrixUtils.getInvertMatrix(deltaMatrix));
    }

}
