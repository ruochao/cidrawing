package com.mocircle.cidrawing;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public interface PaintingBehavior {

    RectF getPointBox(PointF point);

    void drawSelection(Canvas canvas, RectF box);

    void drawReferencePoint(Canvas canvas, PointF point);

    void drawRotationHandle(Canvas canvas, RectF boundingBox);

    RectF getRotationHandleBox(RectF boundingBox);

    void drawResizingHandle(Canvas canvas, RectF boundingBox);

    RectF getNResizingHandleBox(RectF boundingBox);

    RectF getSResizingHandleBox(RectF boundingBox);

    RectF getWResizingHandleBox(RectF boundingBox);

    RectF getEResizingHandleBox(RectF boundingBox);

    RectF getNWResizingHandleBox(RectF boundingBox);

    RectF getNEResizingHandleBox(RectF boundingBox);

    RectF getSWResizingHandleBox(RectF boundingBox);

    RectF getSEResizingHandleBox(RectF boundingBox);

}
