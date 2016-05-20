package com.mocircle.cidrawing;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.utils.ShapeUtils;

/**
 * The default implementation of {@link PaintingBehavior}.
 */
public class DefaultPaintingBehavior implements PaintingBehavior {

    private static final int TOUCH_RADIUS = 40;

    private static final int SELECTION_BOX_OFFSET = 20;

    private static final int REFERENCE_POINT_RADIUS = 15;

    private static final int ROTATION_HANDLE_LENGTH = 80;
    private static final int ROTATION_HANDLE_RADIUS = 15;

    private static final int RESIZING_HANDLE_RADIUS = 15;

    private CiPaint selectionBoundPaint;
    private CiPaint selectionAreaPaint;
    private CiPaint resizingHandlePaint;
    private CiPaint rotationHandlePaint;
    private CiPaint referencePointPaint;

    public DefaultPaintingBehavior(PaintBuilder paintBuilder) {
        this.selectionBoundPaint = paintBuilder.createSelectionBoundPaint();
        this.selectionAreaPaint = paintBuilder.createSelectionAreaPaint();
        this.resizingHandlePaint = paintBuilder.createResizingHandlePaint();
        this.rotationHandlePaint = paintBuilder.createRotationHandlePaint();
        this.referencePointPaint = paintBuilder.createReferencePointPaint();
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
        canvas.drawRect(selectionBox, selectionBoundPaint);
        canvas.drawRect(selectionBox, selectionAreaPaint);
    }

    @Override
    public void drawReferencePoint(Canvas canvas, PointF point) {
        canvas.drawCircle(point.x, point.y, REFERENCE_POINT_RADIUS, referencePointPaint);
    }

    @Override
    public void drawRotationHandle(Canvas canvas, RectF boundingBox) {
        canvas.drawLine(boundingBox.centerX(), boundingBox.top - SELECTION_BOX_OFFSET, boundingBox.centerX(),
                boundingBox.top - SELECTION_BOX_OFFSET - ROTATION_HANDLE_LENGTH, rotationHandlePaint);
        canvas.drawCircle(boundingBox.centerX(), boundingBox.top - SELECTION_BOX_OFFSET - ROTATION_HANDLE_LENGTH - ROTATION_HANDLE_RADIUS,
                ROTATION_HANDLE_RADIUS, rotationHandlePaint);
    }

    @Override
    public RectF getRotationHandleBox(RectF boundingBox) {
        return ShapeUtils.createSquare(boundingBox.centerX(),
                boundingBox.top - SELECTION_BOX_OFFSET - ROTATION_HANDLE_LENGTH - ROTATION_HANDLE_RADIUS,
                TOUCH_RADIUS);
    }

    @Override
    public void drawResizingHandle(Canvas canvas, RectF boundingBox, boolean drawESWNHandles) {

        RectF selectionBox = new RectF(boundingBox.left - SELECTION_BOX_OFFSET, boundingBox.top - SELECTION_BOX_OFFSET,
                boundingBox.right + SELECTION_BOX_OFFSET, boundingBox.bottom + SELECTION_BOX_OFFSET);

        // Draw corner resizing handles
        RectF leftTopRect = ShapeUtils.createSquare(selectionBox.left, selectionBox.top, RESIZING_HANDLE_RADIUS);
        RectF leftBottomRect = ShapeUtils.createSquare(selectionBox.left, selectionBox.bottom, RESIZING_HANDLE_RADIUS);
        RectF rightTopRect = ShapeUtils.createSquare(selectionBox.right, selectionBox.top, RESIZING_HANDLE_RADIUS);
        RectF rightBottomRect = ShapeUtils.createSquare(selectionBox.right, selectionBox.bottom, RESIZING_HANDLE_RADIUS);

        canvas.drawRect(leftTopRect, resizingHandlePaint);
        canvas.drawRect(leftBottomRect, resizingHandlePaint);
        canvas.drawRect(rightTopRect, resizingHandlePaint);
        canvas.drawRect(rightBottomRect, resizingHandlePaint);

        if (drawESWNHandles) {
            // Draw NS resizing handles
            RectF nsTopRect = ShapeUtils.createSquare(selectionBox.centerX(), selectionBox.top, RESIZING_HANDLE_RADIUS);
            RectF nsBottomRect = ShapeUtils.createSquare(selectionBox.centerX(), selectionBox.bottom, RESIZING_HANDLE_RADIUS);

            canvas.drawRect(nsTopRect, resizingHandlePaint);
            canvas.drawRect(nsBottomRect, resizingHandlePaint);

            // Draw EW resizing handles
            RectF ewLeftRect = ShapeUtils.createSquare(selectionBox.left, selectionBox.centerY(), RESIZING_HANDLE_RADIUS);
            RectF ewRightRect = ShapeUtils.createSquare(selectionBox.right, selectionBox.centerY(), RESIZING_HANDLE_RADIUS);

            canvas.drawRect(ewLeftRect, resizingHandlePaint);
            canvas.drawRect(ewRightRect, resizingHandlePaint);
        }
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
