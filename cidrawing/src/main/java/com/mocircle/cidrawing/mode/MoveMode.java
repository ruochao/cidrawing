package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.element.DrawElement;

public class MoveMode extends AbstractDrawingMode {

    private static final String TAG = "MoveMode";

    private PaintBuilder paintBuilder;

    private DrawElement drawElement;
    private Paint originalPaint;

    private float downX;
    private float downY;

    public MoveMode() {
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
        if (!drawElement.isMovementEnabled()) {
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
                float offsetX = event.getX() - downX;
                float offsetY = event.getY() - downY;
                drawElement.move(offsetX, offsetY);
                CircleLog.d(TAG, "Move element by " + offsetX + ", " + offsetY);
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
