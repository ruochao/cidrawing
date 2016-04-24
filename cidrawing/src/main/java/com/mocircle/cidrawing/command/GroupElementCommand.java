package com.mocircle.cidrawing.command;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.GroupElement;
import com.mocircle.cidrawing.utils.SelectionUtils;

import java.util.List;

public class GroupElementCommand extends AbstractCommand {

    private ElementManager elementManager;
    private List<DrawElement> elements;
    private GroupElement groupElement;

    public GroupElementCommand() {
    }

    public GroupElementCommand(List<DrawElement> elements) {
        this.elements = elements;
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public boolean doCommand() {
        // Get current selected elements as group target
        if (elements == null) {
            elements = SelectionUtils.getCurrentSelectedElements(elementManager);
        }
        if (elements.size() <= 1) {
            return false;
        }

        groupElement = new GroupElement(elements);
        for (DrawElement element : elements) {
            elementManager.removeElementFromCurrentLayer(element);
        }
        elementManager.addElementToCurrentLayer(groupElement);

        // Re-select elements
        SelectionUtils.clearSelections(elementManager);
        SelectionUtils.selectElement(groupElement);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undoCommand() {
        if (elements != null) {
            elementManager.removeElementFromCurrentLayer(groupElement);
            for (DrawElement element : elements) {
                elementManager.addElementToCurrentLayer(element);
            }

            // Re-select elements
            SelectionUtils.clearSelections(elementManager);
            SelectionUtils.selectElements(elementManager, elements);
            drawingBoard.getDrawingView().notifyViewUpdated();
        }
    }

}
