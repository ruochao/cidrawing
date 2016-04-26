package com.mocircle.cidrawing.command;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;

public class InsertElementCommand extends AbstractCommand {

    private ElementManager elementManager;
    private DrawElement element;

    public InsertElementCommand(DrawElement element) {
        this.element = element;
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public boolean isExecutable() {
        return element != null;
    }

    @Override
    public boolean doCommand() {
        elementManager.addElementToCurrentLayer(element);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undoCommand() {
        elementManager.removeElementFromCurrentLayer(element);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

}
