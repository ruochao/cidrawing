package com.mocircle.cidrawing.utils;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.VirtualElement;

import java.util.ArrayList;
import java.util.List;

public final class SelectionUtils {

    private SelectionUtils() {
    }

    public static void selectElement(DrawElement element) {
        element.setSelected(true);
    }

    public static void selectElements(ElementManager elementManager, List<DrawElement> elements) {
        VirtualElement virtualElement = new VirtualElement(elements);
        virtualElement.setSelected(true);
        elementManager.addElementToCurrentLayer(virtualElement);
    }

    public static void clearSelections(ElementManager elementManager) {
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            element.setSelected(false);
            if (element instanceof VirtualElement) {
                elementManager.removeElementFromCurrentLayer(element);
            }
        }
    }

    public static List<DrawElement> getCurrentSelectedElements(ElementManager elementManager) {
        List<DrawElement> elements = new ArrayList<>();
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            if (element.isSelected()) {
                if (element instanceof VirtualElement) {
                    elements.addAll(((VirtualElement) element).getElements());
                } else {
                    elements.add(element);
                }
            }
        }
        return elements;
    }

    public static DrawElement getFirstHitElement(ElementManager elementManager, float x, float y) {
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            if (element.isSelectionEnabled()) {
                if (element.hitTestForSelection(x, y)) {
                    return element;
                }
            }
        }
        return null;
    }
}
