package com.mocircle.cidrawing.command;

/**
 * The command to support undo/redo.
 */
public interface DrawingCommand {

    void setDrawingBoardId(String boardId);

    boolean isExecutable();

    boolean doCommand();

    void undoCommand();

    void redoCommand();

}
