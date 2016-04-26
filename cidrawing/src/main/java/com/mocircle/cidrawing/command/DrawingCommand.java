package com.mocircle.cidrawing.command;

public interface DrawingCommand {

    void setDrawingBoardId(String boardId);

    boolean isExecutable();

    boolean doCommand();

    void undoCommand();

    void redoCommand();

}
