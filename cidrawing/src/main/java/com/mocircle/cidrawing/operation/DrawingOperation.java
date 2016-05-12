package com.mocircle.cidrawing.operation;

/**
 * The operation to support undo/redo.
 */
public interface DrawingOperation {

    void setDrawingBoardId(String boardId);

    boolean isExecutable();

    boolean doOperation();

    void undo();

    void redo();

}
