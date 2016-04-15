package com.mocircle.cidrawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.mocircle.cidrawing.utils.ShapeUtils;

public class DefaultPaintingBehavior implements PaintingBehavior {

    private static final int TOUCH_RADIUS = 40;

    private static final int SELECTION_BOX_OFFSET = 20;

    private static final int REFERENCE_POINT_RADIUS = 15;

    private static final int ROTATION_HANDLE_LENGTH = 80;
    private static final int ROTATION_HANDLE_RADIUS = 15;

    private static final int RESIZING_HANDLE_RADIUS = 15;

    private PaintBuilder paintBuilder;

    public DefaultPaintingBehavior(PaintBuilder paintBuilder) {
        this.paintBuilder = paintBuilder;
    }

    @Override
    public RectF getPointBox(PointF point) {
        return ShapeUtils.createSquare(point.x, point.y, TOUCH_RADIUS);
    }

    @Override
    public void drawSelection(Canvas canvas, RectF boundingBox) {
        RectF selectionBox = new RectF(
                boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        canvas.drawRect(selectionBox, paint);

        paint.setAlpha(10);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(selectionBox, paint);
    }

    @Override
    public void drawReferencePoint(Canvas canvas, PointF point) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

        canvas.drawCircle(point.x, point.y, REFERENCE_POINT_RADIUS, paint);
    }

    @Override
    public void drawRotationHandle(Canvas canvas, RectF boundingBox) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

        canvas.drawLine(boundingBox.centerX(), boundingBox.top - SELECTION_BOX_OFFSET, boundingBox.centerX(), boundingBox.top - SELECTION_BOX_OFFSET - ROTATION_HANDLE_LENGTH, paint);
        canvas.drawCircle(boundingBox.centerX(), boundingBox.top - SELECTION_BOX_OFFSET - ROTATION_HANDLE_LENGTH - ROTATION_HANDLE_RADIUS, ROTATION_HANDLE_RADIUS, paint);
    }

    @Override
    public RectF getRotationHandleBox(RectF boundingBox) {
        return ShapeUtils.createSquare(boundingBox.centerX(),
                boundingBox.top - SELECTION_BOX_OFFSET - ROTATION_HANDLE_LENGTH - ROTATION_HANDLE_RADIUS,
                TOUCH_RADIUS);
    }

    @Override
    public void drawResizingHandle(Canvas canvas, RectF boundingBox) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET, boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET, boundingBox.bottom + SELECTION_BOX_OFFSET);

        // Draw corner resizing handles
        RectF leftTopRect = ShapeUtils.createSquare(selectionBox.left, selectionBox.top, RESIZING_HANDLE_RADIUS);
        RectF leftBottomRect = ShapeUtils.createSquare(selectionBox.left, selectionBox.bottom, RESIZING_HANDLE_RADIUS);
        RectF rightTopRect = ShapeUtils.createSquare(selectionBox.right, selectionBox.top, RESIZING_HANDLE_RADIUS);
        RectF rightBottomRect = ShapeUtils.createSquare(selectionBox.right, selectionBox.bottom, RESIZING_HANDLE_RADIUS);

        canvas.drawRect(leftTopRect, paint);
        canvas.drawRect(leftBottomRect, paint);
        canvas.drawRect(rightTopRect, paint);
        canvas.drawRect(rightBottomRect, paint);

        // Draw NS resizing handles
        RectF nsTopRect = ShapeUtils.createSquare(selectionBox.centerX(), selectionBox.top, RESIZING_HANDLE_RADIUS);
        RectF nsBottomRect = ShapeUtils.createSquare(selectionBox.centerX(), selectionBox.bottom, RESIZING_HANDLE_RADIUS);

        canvas.drawRect(nsTopRect, paint);
        canvas.drawRect(nsBottomRect, paint);

        // Draw EW resizing handles
        RectF ewLeftRect = ShapeUtils.createSquare(selectionBox.left, selectionBox.centerY(), RESIZING_HANDLE_RADIUS);
        RectF ewRightRect = ShapeUtils.createSquare(selectionBox.right, selectionBox.centerY(), RESIZING_HANDLE_RADIUS);

        canvas.drawRect(ewLeftRect, paint);
        canvas.drawRect(ewRightRect, paint);
    }

    @Override
    public RectF getNResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.centerX(), selectionBox.top, TOUCH_RADIUS);
    }

    @Override
    public RectF getSResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.centerX(), selectionBox.bottom, TOUCH_RADIUS);
    }

    @Override
    public RectF getWResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.left, selectionBox.centerY(), TOUCH_RADIUS);
    }

    @Override
    public RectF getEResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.right, selectionBox.centerY(), TOUCH_RADIUS);
    }

    @Override
    public RectF getNWResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.left, selectionBox.top, TOUCH_RADIUS);
    }

    @Override
    public RectF getNEResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.right, selectionBox.top, TOUCH_RADIUS);
    }

    @Override
    public RectF getSWResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.left, selectionBox.bottom, TOUCH_RADIUS);
    }

    @Override
    public RectF getSEResizingHandleBox(RectF boundingBox) {
        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET,
                boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET,
                boundingBox.bottom + SELECTION_BOX_OFFSET);
        return ShapeUtils.createSquare(selectionBox.right, selectionBox.bottom, TOUCH_RADIUS);
    }


}
