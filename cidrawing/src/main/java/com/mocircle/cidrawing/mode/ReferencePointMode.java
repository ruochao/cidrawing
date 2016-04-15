package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.element.DrawElement;

public class ReferencePointMode extends AbstractDrawingMode {

    private static final String TAG = "ReferencePointMode";

    private PaintBuilder paintBuilder;

    private DrawElement drawElement;
    private Paint originalPaint;

    private float downX;
    private float downY;

    public ReferencePointMode() {
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
        if (!drawElement.hasReferencePoint()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                Paint p = paintBuilder.createPreviewPaint(drawElement.getPaint());
                originalPaint = new Paint(drawElement.getPaint());
                drawElement.setPaint(p);
                return true;
            case MotionEvent.ACTION_MOVE:
                float[] points = new float[4];
                drawElement.getInvertedDisplayMatrix().mapPoints(points, new float[]{downX, downY, event.getX(), event.getY()});
                float dx = points[2] - points[0];
                float dy = points[3] - points[1];
                drawElement.getReferencePoint().offset(dx, dy);
                CircleLog.d(TAG, "Move reference point by " + dx + ", " + dy);
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                drawElement.setPaint(originalPaint);
                return true;
        }
        return false;
    }
}
