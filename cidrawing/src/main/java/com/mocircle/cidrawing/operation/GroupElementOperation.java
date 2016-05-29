package com.mocircle.cidrawing.operation;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.GroupElement;

import java.util.List;

public class GroupElementOperation extends SelectedElementsOperation {

    private GroupElement groupElement;

    public GroupElementOperation() {
    }

    public GroupElementOperation(List<DrawElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean doOperation() {
        groupElement = new GroupElement(elements);
        for (DrawElement element : elements) {
            elementManager.removeElementFromCurrentLayer(element);
        }
        elementManager.addElementToCurrentLayer(groupElement);

        // Re-select elements
        elementManager.clearSelection();
        elementManager.selectElement(groupElement);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        if (elements != null) {
            elementManager.removeElementFromCurrentLayer(groupElement);
            for (DrawElement element : elements) {
                elementManager.addElementToCurrentLayer(element);
            }

            // Re-select elements
            elementManager.clearSelection();
            elementManager.selectElements(elements);
            drawingBoard.getDrawingView().notifyViewUpdated();
        }
    }

}
