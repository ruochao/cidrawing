package com.mocircle.cidrawing.board;

import com.mocircle.cidrawing.element.DrawElement;

import java.util.List;

public interface ElementManager extends LayerManager {

    DrawElement[] getVisibleObjects();

    DrawElement[] getVisibleElements();

    DrawElement[] getVisibleAdornments();

    DrawElement[] getCurrentObjects();

    DrawElement[] getCurrentElements();

    DrawElement[] getCurrentAdornments();

    void addElementToCurrentLayer(DrawElement element);

    void removeElementFromCurrentLayer(DrawElement element);

    void addAdornmentToCurrentLayer(DrawElement element);

    void removeAdornmentFromCurrentLayer(DrawElement element);

    void selectElement(DrawElement element);

    void selectElements(List<DrawElement> elements);

    Selection getSelection();

    void clearSelection();

    DrawElement getFirstHitElement(float x, float y);

}
