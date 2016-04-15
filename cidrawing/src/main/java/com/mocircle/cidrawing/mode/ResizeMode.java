package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.element.DrawElement;

public class ResizeMode extends AbstractDrawingMode {

    private static final String TAG = "ResizeMode";

    private PaintBuilder paintBuilder;

    private float downX;
    private float downY;

    private DrawElement drawElement;
    private Paint originalPaint;
    private ResizingDirection direction;
    private boolean keepAspectRatio;

    public ResizeMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        paintBuilder = drawingBoard.getPaintBuilder();
    }

    public void setCurrentElement(DrawElement element) {
        drawElement = element;
    }

    public void setResizingDirection(ResizingDirection direction) {
        this.direction = direction;
    }

    public void setKeepAspectRatio(boolean keepAspectRatio) {
        this.keepAspectRatio = keepAspectRatio;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!drawElement.isResizingEnabled()) {
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
                resizeElement(downX, downY, event.getX(), event.getY());
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


    private void resizeElement(float x1, float y1, float x2, float y2) {
        float[] points = new float[4];
        drawElement.getInvertedDisplayMatrix().mapPoints(points, new float[]{x1, y1, x2, y2});
        RectF box = drawElement.getBoundingBox();

        float sx = 1;
        float sy = 1;
        float px = 0;
        float py = 0;
        switch (direction) {
            case NORTH:
                px = box.right;
                py = box.bottom;
                sx = 1;
                sy = (box.height() + (points[1] - points[3])) / box.height();
                break;
            case SOUTH:
                px = box.left;
                py = box.top;
                sx = 1;
                sy = (box.height() + (points[3] - points[1])) / box.height();
                break;
            case WEST:
                px = box.right;
                py = box.bottom;
                sx = (box.width() + (points[0] - points[2])) / box.width();
                sy = 1;
                break;
            case EAST:
                px = box.left;
                py = box.top;
                sx = (box.width() + (points[2] - points[0])) / box.width();
                sy = 1;
                break;
            case NORTH_WEST:
                px = box.right;
                py = box.bottom;
                sx = (box.width() + (points[0] - points[2])) / box.width();
                sy = (box.height() + (points[1] - points[3])) / box.height();
                if (keepAspectRatio) {
                    sx = getUniformScale(sx, sy);
                    sy = getUniformScale(sx, sy);
                }
                break;
            case NORTH_EAST:
                px = box.left;
                py = box.bottom;
                sx = (box.width() + (points[2] - points[0])) / box.width();
                sy = (box.height() + (points[1] - points[3])) / box.height();
                if (keepAspectRatio) {
                    sx = getUniformScale(sx, sy);
                    sy = getUniformScale(sx, sy);
                }
                break;
            case SOUTH_WEST:
                px = box.right;
                py = box.top;
                sx = (box.width() + (points[0] - points[2])) / box.width();
                sy = (box.height() + (points[3] - points[1])) / box.height();
                if (keepAspectRatio) {
                    sx = getUniformScale(sx, sy);
                    sy = getUniformScale(sx, sy);
                }
                break;
            case SOUTH_EAST:
                px = box.left;
                py = box.top;
                sx = (box.width() + (points[2] - points[0])) / box.width();
                sy = (box.height() + (points[3] - points[1])) / box.height();
                if (keepAspectRatio) {
                    sx = getUniformScale(sx, sy);
                    sy = getUniformScale(sx, sy);
                }
                break;
        }

        // Do resizing
        if (sx > 0 && sy > 0) {
            drawElement.resize(sx, sy, px, py);
        }
    }

    private float getUniformScale(float sx, float sy) {
        if (Math.abs(sx - 1) > Math.abs(sy - 1)) {
            return sx;
        } else {
            return sy;
        }
    }


}