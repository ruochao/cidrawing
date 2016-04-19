package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.behavior.Selectable;
import com.mocircle.cidrawing.utils.SelectionUtils;

public class SkewMode extends AbstractDrawingMode {

    private static final String TAG = "SkewMode";

    private ElementManager elementManager;
    private PaintBuilder paintBuilder;

    private DrawElement drawElement;
    private Selectable.SelectionStyle previousSelectionStyle;
    private Paint originalPaint;
    private PointF referencePoint;

    private float downX;
    private float downY;

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        paintBuilder = drawingBoard.getPaintBuilder();
    }

    @Override
    public void onLeaveMode() {
        if (drawElement != null) {
            drawElement.setSelected(false);
            drawElement.setSelectionStyle(previousSelectionStyle);
        }
    }

    public void setCurrentElement(DrawElement element) {
        drawElement = element;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawElement != null && !drawElement.isSkewEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                detectElement();

                if (drawElement != null) {
                    referencePoint = drawElement.getReferencePoint();

                    Paint p = paintBuilder.createPreviewPaint(drawElement.getPaint());
                    originalPaint = new Paint(drawElement.getPaint());
                    drawElement.setPaint(p);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (drawElement != null) {
                    skewElement(downX, downY, event.getX(), event.getY());
                }
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (drawElement != null) {
                    drawElement.setPaint(originalPaint);
                }
                return true;
        }
        return false;
    }

    private void skewElement(float x1, float y1, float x2, float y2) {
        float[] points = new float[4];
        drawElement.getInvertedDisplayMatrix().mapPoints(points, new float[]{x1, y1, x2, y2});
        RectF box = drawElement.getBoundingBox();

        float kx = (points[2] - points[0]) / box.height();
        float ky = (points[3] - points[1]) / box.width();

        drawElement.skew(kx, ky, referencePoint.x, referencePoint.y);
        CircleLog.d(TAG, "Skew element by " + kx + ", " + ky);
    }

    private void detectElement() {
        SelectionUtils.clearSelections(elementManager);
        if (drawElement != null) {
            drawElement.setSelectionStyle(previousSelectionStyle);
        }
        drawElement = SelectionUtils.getFirstHitElement(elementManager, downX, downY);
        if (drawElement != null) {
            previousSelectionStyle = drawElement.getSelectionStyle();
            drawElement.setSelected(true);
            drawElement.setSelectionStyle(Selectable.SelectionStyle.LIGHT);
        }
    }

}