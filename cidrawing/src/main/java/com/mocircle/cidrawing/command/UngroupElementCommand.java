package com.mocircle.cidrawing.command;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.GroupElement;
import com.mocircle.cidrawing.utils.SelectionUtils;

import java.util.List;

public class UngroupElementCommand extends AbstractCommand {

    private ElementManager elementManager;
    private GroupElement groupElement;

    public UngroupElementCommand() {
    }

    public UngroupElementCommand(GroupElement groupElement) {
        this.groupElement = groupElement;
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public boolean doCommand() {
        // Get current selected elements as group target
        if (groupElement == null) {
            List<DrawElement> elements = SelectionUtils.getCurrentSelectedElements(elementManager);
            if (elements.size() == 1 && elements.get(0) instanceof GroupElement) {
                groupElement = (GroupElement) elements.get(0);
            }
        }
        if (groupElement == null) {
            return false;
        }

        for (DrawElement element : groupElement.getElements()) {
            element.getDisplayMatrix().postConcat(groupElement.getDisplayMatrix());
            elementManager.addElementToCurrentLayer(element);
        }
        elementManager.removeElementFromCurrentLayer(groupElement);

        // Re-select elements
        SelectionUtils.clearSelections(elementManager);
        SelectionUtils.selectElements(elementManager, groupElement.getElements());
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undoCommand() {
        if (groupElement != null) {
            for (DrawElement element : groupElement.getElements()) {
                elementManager.removeElementFromCurrentLayer(element);
            }
            elementManager.addElementToCurrentLayer(groupElement);

            // Re-select elements
            SelectionUtils.clearSelections(elementManager);
            SelectionUtils.selectElement(groupElement);
            drawingBoard.getDrawingView().notifyViewUpdated();
        }
    }

}
