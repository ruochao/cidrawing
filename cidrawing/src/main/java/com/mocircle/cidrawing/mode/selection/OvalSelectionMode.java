package com.mocircle.cidrawing.mode.selection;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.shape.OvalElement;

public class OvalSelectionMode extends ShapeSelectionMode {

    public OvalSelectionMode() {
    }

    @Override
    protected DrawElement createSelectionElement() {
        return new OvalElement();
    }

}
