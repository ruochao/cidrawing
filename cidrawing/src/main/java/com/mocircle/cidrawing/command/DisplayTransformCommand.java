package com.mocircle.cidrawing.command;

import android.graphics.Matrix;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.utils.MatrixUtils;

public class DisplayTransformCommand extends AbstractCommand {

    protected DrawElement element;
    protected Matrix deltaMatrix;

    public DisplayTransformCommand(DrawElement element, Matrix deltaMatrix) {
        this.element = element;
        this.deltaMatrix = deltaMatrix;
    }

    @Override
    public boolean isExecutable() {
        return element != null;
    }

    @Override
    public boolean doCommand() {
        element.getDisplayMatrix().postConcat(deltaMatrix);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undoCommand() {
        element.getDisplayMatrix().postConcat(MatrixUtils.getInvertMatrix(deltaMatrix));
        drawingBoard.getDrawingView().notifyViewUpdated();
    }
}
