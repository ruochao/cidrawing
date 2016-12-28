package com.mocircle.cidrawing.operation;

import com.mocircle.cidrawing.board.ArrangeStrategy;
import com.mocircle.cidrawing.board.Layer;
import com.mocircle.cidrawing.element.DrawElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrangeOperation extends SelectedElementsOperation {

    public enum ArrangeType {
        None,
        BringForward,
        BringToFront,
        SendBackward,
        SendToBack,
    }

    private ArrangeType arrangeType = ArrangeType.None;
    private ArrangeStrategy arrangeStrategy = ArrangeStrategy.WITH_FIXED_DISTANCE;
    private Map<DrawElement, Integer> orderMap = new HashMap<>();

    public ArrangeOperation() {
    }

    public ArrangeOperation(List<DrawElement> elements) {
        this.elements = elements;
    }

    public ArrangeType getArrangeType() {
        return arrangeType;
    }

    public void setArrangeType(ArrangeType arrangeType) {
        this.arrangeType = arrangeType;
    }

    public ArrangeStrategy getArrangeStrategy() {
        return arrangeStrategy;
    }

    public void setArrangeStrategy(ArrangeStrategy arrangeStrategy) {
        this.arrangeStrategy = arrangeStrategy;
    }

    @Override
    public boolean doOperation() {
        // Save current elements ordering
        Layer currentLayer = drawingBoard.getElementManager().getCurrentLayer();
        for (DrawElement element : elements) {
            orderMap.put(element, currentLayer.getOrderIndex(element));
        }
        switch (arrangeType) {
            case BringForward:
                if (elements.size() == 1) {
                    currentLayer.arrangeElement(elements.get(0), 1);
                } else {
                    currentLayer.arrangeElements(elements, 1, arrangeStrategy);
                }
                break;
            case SendBackward:
                if (elements.size() == 1) {
                    currentLayer.arrangeElement(elements.get(0), -1);
                } else {
                    currentLayer.arrangeElements(elements, -1, arrangeStrategy);
                }
                break;
            case BringToFront:
                if (elements.size() == 1) {
                    currentLayer.arrangeElementToFront(elements.get(0));
                } else {
                    currentLayer.arrangeElementsToFront(elements, arrangeStrategy);
                }
                break;
            case SendToBack:
                if (elements.size() == 1) {
                    currentLayer.arrangeElementToBack(elements.get(0));
                } else {
                    currentLayer.arrangeElementsToBack(elements, arrangeStrategy);
                }
                break;
        }
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        Layer currentLayer = drawingBoard.getElementManager().getCurrentLayer();
        for (DrawElement element : elements) {
            int currentOrder = currentLayer.getOrderIndex(element);
            int offset = orderMap.get(element) - currentOrder;
            currentLayer.arrangeElement(element, offset);
        }
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

    protected int minimumSelectedElements() {
        return 1;
    }

}
