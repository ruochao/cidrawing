package com.mocircle.cidrawing;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class DefaultPaintBuilder implements PaintBuilder {

    @Override
    public Paint createDebugPaintForLine() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }

    @Override
    public Paint createDebugPaintForArea() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(10);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public Paint createPreviewPaint(Paint originalPaint) {
        Paint paint = new Paint(originalPaint);
        paint.setAlpha(paint.getAlpha() / 2);
        return paint;
    }

    @Override
    public Paint createRectSelectionToolPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        return paint;
    }

    @Override
    public Paint createSelectionBoundPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        return paint;
    }

    @Override
    public Paint createSelectionAreaPaint() {
        Paint paint = createSelectionBoundPaint();
        paint.setAlpha(10);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public Paint createResizingHandlePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public Paint createRotationHandlePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        return paint;
    }

    @Override
    public Paint createReferencePointPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        return paint;
    }


}
