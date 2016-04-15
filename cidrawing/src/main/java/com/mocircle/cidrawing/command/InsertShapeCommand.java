package com.mocircle.cidrawing.command;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.shape.ShapeElement;

public class InsertShapeCommand extends AbstractCommand {

    private ElementManager elementManager;
    private ShapeElement element;

    public InsertShapeCommand(ShapeElement element) {
        this.element = element;
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public void doCommand() {
        elementManager.addElementToCurrentLayer(element);
    }

    @Override
    public void undoCommand() {
        elementManager.removeElementFromCurrentLayer(element);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

    @Override
    public void redoCommand() {
        doCommand();
        drawingBoard.getDrawingView().notifyViewUpdated();
    }
}
