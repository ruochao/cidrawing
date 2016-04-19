package com.mocircle.cidrawing.element;

import android.graphics.Canvas;

import java.util.List;

public class VirtualElement extends ElementGroup {

    public VirtualElement() {
        super();
    }

    public VirtualElement(List<DrawElement> elements) {
        super(elements);
    }

    @Override
    public Object clone() {
        VirtualElement element = new VirtualElement();
        cloneTo(element);
        return element;
    }

    @Override
    public void drawElement(Canvas canvas) {
        // Draw nothing but debug info
        if (configManager.isDebugMode()) {
            canvas.drawRect(boundingBox, debugPaintForArea);
        }
    }

}
