package com.mocircle.cidrawing.operation;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.GroupElement;

public class UngroupElementOperation extends AbstractOperation {

    private ElementManager elementManager;
    private GroupElement groupElement;

    public UngroupElementOperation() {
    }

    public UngroupElementOperation(GroupElement groupElement) {
        this.groupElement = groupElement;
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public boolean isExecutable() {
        if (groupElement == null) {
            DrawElement element = elementManager.getSelection().getSingleElement();
            if (element instanceof GroupElement) {
                groupElement = (GroupElement) element;
            }
        }
        return groupElement != null;
    }

    @Override
    public boolean doOperation() {
        for (DrawElement element : groupElement.getElements()) {
            element.getDisplayMatrix().postConcat(groupElement.getDisplayMatrix());
            elementManager.addElementToCurrentLayer(element);
        }
        elementManager.removeElementFromCurrentLayer(groupElement);

        // Re-select elements
        elementManager.clearSelection();
        elementManager.selectElements(groupElement.getElements());
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        if (groupElement != null) {
            for (DrawElement element : groupElement.getElements()) {
                elementManager.removeElementFromCurrentLayer(element);
            }
            elementManager.addElementToCurrentLayer(groupElement);

            // Re-select elements
            elementManager.clearSelection();
            elementManager.selectElement(groupElement);
            drawingBoard.getDrawingView().notifyViewUpdated();
        }
    }

}
