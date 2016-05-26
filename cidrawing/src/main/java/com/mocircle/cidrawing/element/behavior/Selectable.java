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

    /**
     * Checks if the given point is inside the element's touch area.
     *
     * @param x axis x of given point
     * @param y axis y of given point
     * @return true if hit test successful, otherwise false.
     */
    boolean hitTestForSelection(float x, float y);

    /**
     * Checks if the given path is including the element's touch area.
     *
     * @param path given path
     * @return true if hit test successful, otherwise false.
     */
    boolean hitTestForSelection(Path path);
}
