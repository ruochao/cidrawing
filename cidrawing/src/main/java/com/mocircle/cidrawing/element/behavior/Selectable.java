package com.mocircle.cidrawing.element.behavior;

import android.graphics.Canvas;
import android.graphics.Path;

public interface Selectable {

    enum SelectionStyle {
        FULL,
        LIGHT
    }

    boolean isSelectionEnabled();

    void setSelectionEnabled(boolean selectionEnabled);

    boolean isSelected();

    void setSelected(boolean selected);

    void setSelected(boolean selected, SelectionStyle selectionStyle);

    SelectionStyle getSelectionStyle();

    void drawSelection(Canvas canvas);

    boolean hitTestForSelection(float x, float y);

    boolean hitTestForSelection(Path path);
}
