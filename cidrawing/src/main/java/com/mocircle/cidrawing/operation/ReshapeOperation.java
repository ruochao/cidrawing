package com.mocircle.cidrawing.operation;

import android.graphics.Matrix;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;

public class ReshapeOperation extends AbstractOperation {

    private ElementManager elementManager;
    private DrawElement reshapeElement;
    private Matrix displayMatrix;

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public boolean isExecutable() {
        if (reshapeElement == null) {
            reshapeElement = elementManager.getSelection().getSingleElement();
        }
        return reshapeElement != null;
    }

    @Override
    public boolean doOperation() {
        displayMatrix = reshapeElement.applyDisplayMatrixToData();
        reshapeElement.resetReferencePoint();
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        if (reshapeElement != null) {
            reshapeElement.restoreDisplayMatrixFromData(displayMatrix);
            drawingBoard.getDrawingView().notifyViewUpdated();
        }
    }

}
