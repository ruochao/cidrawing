package com.mocircle.cidrawing.mode.transformation;

import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.element.behavior.Selectable;

public class SkewMode extends DataTransformMode {

    private static final String TAG = "SkewMode";

    private PointF referencePoint;

    private float downX;
    private float downY;

    public SkewMode() {
    }

    public SkewMode(boolean autoDetectMode) {
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
        if (element == null || !element.isSkewEnabled()) {
            return result;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                referencePoint = element.getReferencePoint();
                return true;
            case MotionEvent.ACTION_MOVE:
                skewElement(downX, downY, event.getX(), event.getY());
                downX = event.getX();
                downY = event.getY();
                return true;
        }
        return result;
    }

    private void skewElement(float x1, float y1, float x2, float y2) {
        float[] points = new float[4];
        element.getInvertedDisplayMatrix().mapPoints(points, new float[]{x1, y1, x2, y2});
        RectF box = element.getBoundingBox();

        float kx = (points[2] - points[0]) / box.height();
        float ky = (points[3] - points[1]) / box.width();

        element.skew(kx, ky, referencePoint.x, referencePoint.y);
        CircleLog.d(TAG, "Skew element by " + kx + ", " + ky);
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