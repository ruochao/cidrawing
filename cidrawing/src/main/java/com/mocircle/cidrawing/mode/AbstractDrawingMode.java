package com.mocircle.cidrawing.mode;

import com.mocircle.cidrawing.DrawingBoard;
import com.mocircle.cidrawing.DrawingBoardManager;

public abstract class AbstractDrawingMode implements DrawingMode {

    protected DrawingBoard drawingBoard;

    public AbstractDrawingMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        drawingBoard = DrawingBoardManager.getInstance().findDrawingBoard(boardId);
    }

    @Override
    public void onEnterMode() {
    }

    @Override
    public void onLeaveMode() {
    }

}
