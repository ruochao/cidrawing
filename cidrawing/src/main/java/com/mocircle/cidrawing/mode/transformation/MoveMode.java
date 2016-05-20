package com.mocircle.cidrawing.mode.transformation;

import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.element.behavior.Selectable;
import com.mocircle.cidrawing.mode.transformation.DisplayTransformMode;

public class MoveMode extends DisplayTransformMode {

    private static final String TAG = "MoveMode";

    private float downX;
    private float downY;

    public MoveMode() {
    }

    public MoveMode(boolean autoDetectMode) {
        super(autoDetectMode);
    }

    @Override
    public void onLeaveMode() {
        if (element != null) {
            element.setSelected(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (element == null || !element.isMovementEnabled()) {
            return result;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float offsetX = event.getX() - downX;
                float offsetY = event.getY() - downY;
                element.move(offsetX, offsetY);
                CircleLog.d(TAG, "Move element by " + offsetX + ", " + offsetY);
                CircleLog.v(TAG, "Element position: " + element.getLocX() + ", " + element.getLocY());
                downX = event.getX();
                downY = event.getY();
                return true;
        }
        return result;
    }

    @Override
    protected void detectElement(float x, float y) {
        super.detectElement(x, y);
        elementManager.clearSelection();
        if (element != null) {
            element.setSelected(true, Selectable.SelectionStyle.LIGHT);
        }
    }

}
