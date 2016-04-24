package com.mocircle.cidrawing.command;

import android.graphics.Matrix;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.utils.MatrixUtils;

public class DataTransformCommand extends AbstractCommand {

    private DrawElement element;
    private Matrix deltaMatrix;

    public DataTransformCommand(DrawElement element, Matrix deltaMatrix) {
        this.element = element;
        this.deltaMatrix = deltaMatrix;
    }

    @Override
    public boolean doCommand() {
        element.applyMatrixForData(deltaMatrix);
        element.updateBoundingBox();
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undoCommand() {
        element.applyMatrixForData(MatrixUtils.getInvertMatrix(deltaMatrix));
        element.updateBoundingBox();
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

}
