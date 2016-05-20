package com.mocircle.cidrawing.mode.transformation;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.mode.ElementOperationMode;
import com.mocircle.cidrawing.operation.MovePointOperation;

public class MoveReferencePointMode extends ElementOperationMode {

    private static final String TAG = "MoveReferencePointMode";

    private PointF originalReferencePoint;

    private float downX;
    private float downY;

    public MoveReferencePointMode() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (element == null || !element.hasReferencePoint()) {
            return result;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                originalReferencePoint = new PointF(element.getReferencePoint().x, element.getReferencePoint().y);
                return true;
            case MotionEvent.ACTION_MOVE:
                float[] points = new float[4];
                element.getInvertedDisplayMatrix().mapPoints(points, new float[]{downX, downY, event.getX(), event.getY()});
                float dx = points[2] - points[0];
                float dy = points[3] - points[1];
                element.getReferencePoint().offset(dx, dy);
                CircleLog.d(TAG, "Move reference point by " + dx + ", " + dy);
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                float deltaX = element.getReferencePoint().x - originalReferencePoint.x;
                float deltaY = element.getReferencePoint().y - originalReferencePoint.y;
                resetPointOffset(deltaX, deltaY);
                operationManager.executeOperation(new MovePointOperation(element.getReferencePoint(), deltaX, deltaY));
                return true;
            case MotionEvent.ACTION_CANCEL:
                deltaX = element.getReferencePoint().x - originalReferencePoint.x;
                deltaY = element.getReferencePoint().y - originalReferencePoint.y;
                resetPointOffset(deltaX, deltaY);
                return true;
        }
        return false;
    }

    private void resetPointOffset(float deltaX, float deltaY) {
        element.getReferencePoint().offset(-deltaX, -deltaY);
    }

}
