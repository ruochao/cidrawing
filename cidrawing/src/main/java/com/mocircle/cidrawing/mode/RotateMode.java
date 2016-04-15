package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.utils.ShapeUtils;

public class RotateMode extends AbstractDrawingMode {

    private static final String TAG = "RotateMode";

    private PaintBuilder paintBuilder;

    private DrawElement drawElement;
    private Paint originalPaint;
    private PointF referencePoint;

    private float downX;
    private float downY;

    public RotateMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        paintBuilder = drawingBoard.getPaintBuilder();
    }

    public void setCurrentElement(DrawElement element) {
        drawElement = element;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!drawElement.isRotationEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                referencePoint = drawElement.getActualReferencePoint();

                Paint p = paintBuilder.createPreviewPaint(drawElement.getPaint());
                originalPaint = new Paint(drawElement.getPaint());
                drawElement.setPaint(p);
                return true;
            case MotionEvent.ACTION_MOVE:
                float degreeDelta = ShapeUtils.calculateRotationDegree(referencePoint,
                        new PointF(downX, downY), new PointF(event.getX(), event.getY()));
                drawElement.rotate(degreeDelta, referencePoint.x, referencePoint.y);
                downX = event.getX();
                downY = event.getY();
                CircleLog.d(TAG, "Rotate element by " + degreeDelta);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                drawElement.setPaint(originalPaint);
                return true;
        }
        return false;
    }

}