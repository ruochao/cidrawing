package com.mocircle.cidrawing.element.behavior;

import android.graphics.Canvas;

public interface Selectable {

    enum SelectionStyle {
        FULL,
        LIGHT
    }

    boolean isSelectionEnabled();

    void setSelectionEnabled(boolean selectionEnabled);

    boolean isSelected();

    void setSelected(boolean selected);

    SelectionStyle getSelectionStyle();

    void setSelectionStyle(SelectionStyle selectionStyle);

    void drawSelection(Canvas canvas);

    boolean hitTestForSelection(float x, float y);

    boolean hitTestForSelection(float x1, float y1, float x2, float y2);
}
