package com.mocircle.cidrawing.command;

public interface DrawingCommand {

    void setDrawingBoardId(String boardId);

    boolean doCommand();

    void undoCommand();

    void redoCommand();

}
