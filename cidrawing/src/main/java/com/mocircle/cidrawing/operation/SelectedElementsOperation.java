package com.mocircle.cidrawing.operation;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;

import java.util.List;

public abstract class SelectedElementsOperation extends AbstractOperation {

    protected ElementManager elementManager;
    protected List<DrawElement> elements;

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
    }

    @Override
    public boolean isExecutable() {
        if (elements == null) {
            // Get current selected elements
            elements = elementManager.getSelection().getElements();
        }
        return elements.size() >= minimumSelectedElements();
    }

    protected int minimumSelectedElements() {
        return 2;
    }

}
