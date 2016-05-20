package com.mocircle.cidrawing.mode.eraser;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.mode.BasePointMode;
import com.mocircle.cidrawing.operation.OperationManager;
import com.mocircle.cidrawing.operation.RemoveElementOperation;

public class ObjectEraserMode extends BasePointMode {

    private ElementManager elementManager;
    private OperationManager operationManager;

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        operationManager = drawingBoard.getOperationManager();
    }

    @Override
    protected void onOverPoint(float x, float y) {
        DrawElement element = elementManager.getFirstHitElement(x, y);
        if (element != null) {
            operationManager.executeOperation(new RemoveElementOperation(element));
        }
    }
}
