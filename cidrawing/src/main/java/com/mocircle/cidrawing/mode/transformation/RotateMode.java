package com.mocircle.cidrawing.mode.transformation;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.element.behavior.Selectable;
import com.mocircle.cidrawing.utils.ShapeUtils;

public class RotateMode extends DisplayTransformMode {

    private static final String TAG = "RotateMode";

    private float downX;
    private float downY;
    private PointF referencePoint;

    public RotateMode() {
    }

    public RotateMode(boolean autoDetectMode) {
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
        if (element == null || !element.isRotationEnabled()) {
            return result;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                referencePoint = element.getActualReferencePoint();
                return true;
            case MotionEvent.ACTION_MOVE:
                float degreeDelta = ShapeUtils.calculateRotationDegree(referencePoint,
                        new PointF(downX, downY), new PointF(event.getX(), event.getY()));
                element.rotate(degreeDelta, referencePoint.x, referencePoint.y);
                downX = event.getX();
                downY = event.getY();
                CircleLog.d(TAG, "Rotate element by " + degreeDelta);
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