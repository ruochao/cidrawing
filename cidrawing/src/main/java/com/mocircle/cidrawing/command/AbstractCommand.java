package com.mocircle.cidrawing.command;

import com.mocircle.cidrawing.DrawingBoard;
import com.mocircle.cidrawing.DrawingBoardManager;

public abstract class AbstractCommand implements DrawingCommand {

    protected DrawingBoard drawingBoard;

    @Override
    public void setDrawingBoardId(String boardId) {
        this.drawingBoard = DrawingBoardManager.getInstance().findDrawingBoard(boardId);
    }

    @Override
    public void redoCommand() {
        doCommand();
    }
}
