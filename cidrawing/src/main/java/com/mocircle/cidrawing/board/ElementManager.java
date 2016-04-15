package com.mocircle.cidrawing.board;

import com.mocircle.cidrawing.element.DrawElement;

public interface ElementManager extends LayerManager {

    DrawElement[] getVisibleElements();

    DrawElement[] getCurrentElements();

    void addElementToCurrentLayer(DrawElement element);

    void removeElementFromCurrentLayer(DrawElement element);

}
