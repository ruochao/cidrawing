package com.mocircle.cidrawing;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * A interface defines how to draw things and the related behaviors.
 */
public interface PaintingBehavior {

    /**
     * Gets the box of a point
     *
     * @param point
     * @return
     */
    RectF getPointBox(PointF point);

    /**
     * Draws the element selection.
     *
     * @param canvas drawing canvas
     * @param box    element bounding box
     */
    void drawSelection(Canvas canvas, RectF box);

    void drawReferencePoint(Canvas canvas, PointF point);

    void drawRotationHandle(Canvas canvas, RectF boundingBox);

    RectF getRotationHandleBox(RectF boundingBox);

    void drawResizingHandle(Canvas canvas, RectF boundingBox, boolean drawESWNHandles);

    RectF getNResizingHandleBox(RectF boundingBox);

    RectF getSResizingHandleBox(RectF boundingBox);

    RectF getWResizingHandleBox(RectF boundingBox);

    RectF getEResizingHandleBox(RectF boundingBox);

    RectF getNWResizingHandleBox(RectF boundingBox);

    RectF getNEResizingHandleBox(RectF boundingBox);

    RectF getSWResizingHandleBox(RectF boundingBox);

    RectF getSEResizingHandleBox(RectF boundingBox);

}
