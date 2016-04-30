package com.mocircle.cidrawing.board;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.VirtualElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Element selection class.
 */
public class Selection {

    private DrawElement element;

    Selection(DrawElement element) {
        this.element = element;
    }

    public boolean isEmptySelection() {
        return element == null;
    }

    public boolean isMultipleSelection() {
        return element instanceof VirtualElement;
    }

    public DrawElement getSingleElement() {
        if (isMultipleSelection()) {
            return null;
        } else {
            return element;
        }
    }

    public List<DrawElement> getElements() {
        if (isEmptySelection()) {
            return new ArrayList<>();
        }
        if (isMultipleSelection()) {
            VirtualElement virtualElement = (VirtualElement) element;
            return virtualElement.getElements();
        } else {
            List<DrawElement> elements = new ArrayList<>();
            elements.add(element);
            return elements;
        }
    }

}
