package com.mocircle.cidrawing.command;

public interface DrawingCommand {

    void setDrawingBoardId(String boardId);

    void doCommand();

    void undoCommand();

    void redoCommand();

}
