package com.mocircle.cidrawing.mode.selection;

import android.graphics.Path;
import android.view.MotionEvent;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.shape.ShapeElement;

public abstract class ShapeSelectionMode extends SelectionMode {

    private static final String TAG = "ShapeSelectionMode";

    public ShapeSelectionMode() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (selectionElement instanceof ShapeElement) {
                    ((ShapeElement) selectionElement).setupElementByVector(new Vector2(downX, downY, event.getX(), event.getY()));
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected Path getSelectionPath() {
        if (selectionElement instanceof ShapeElement) {
            return ((ShapeElement) selectionElement).getElementPath();
        } else {
            return null;
        }
    }

}
