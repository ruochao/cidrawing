package com.mocircle.cidrawing.mode.selection;

import android.graphics.Path;
import android.view.MotionEvent;

import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.StrokeElement;

public class LassoSelectionMode extends SelectionMode {

    public LassoSelectionMode() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (selectionElement instanceof StrokeElement) {
                    ((StrokeElement) selectionElement).addPoint(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (selectionElement instanceof StrokeElement) {
                    ((StrokeElement) selectionElement).doneEditing();
                }
                return super.onTouchEvent(event);
            default:
                return super.onTouchEvent(event);
        }
    }

    protected DrawElement createSelectionElement() {
        StrokeElement element = new StrokeElement();
        element.setCloseStroke(true);
        return element;
    }

    @Override
    protected Path getSelectionPath() {
        if (selectionElement instanceof StrokeElement) {
            return ((StrokeElement) selectionElement).getElementPath();
        } else {
            return null;
        }
    }

}
