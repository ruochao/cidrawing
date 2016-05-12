package com.mocircle.cidrawing.operation;

import com.mocircle.cidrawing.DrawingBoard;
import com.mocircle.cidrawing.DrawingBoardManager;

public abstract class AbstractOperation implements DrawingOperation {

    protected DrawingBoard drawingBoard;

    @Override
    public void setDrawingBoardId(String boardId) {
        this.drawingBoard = DrawingBoardManager.getInstance().findDrawingBoard(boardId);
    }

    @Override
    public void redo() {
        doOperation();
    }
}
