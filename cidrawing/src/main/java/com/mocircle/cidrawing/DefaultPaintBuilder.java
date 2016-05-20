package com.mocircle.cidrawing;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.mocircle.cidrawing.core.CiPaint;

/**
 * The default implementation of {@link PaintBuilder}.
 */
public class DefaultPaintBuilder implements PaintBuilder {

    @Override
    public CiPaint createDebugPaintForLine() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }

    @Override
    public CiPaint createDebugPaintForArea() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.RED);
        paint.setAlpha(10);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public CiPaint createPreviewPaint(CiPaint originalPaint) {
        CiPaint paint = new CiPaint(originalPaint);
        paint.setAlpha(paint.getAlpha() / 2);
        return paint;
    }

    @Override
    public CiPaint createPreviewAreaPaint(CiPaint originalPaint) {
        CiPaint paint = new CiPaint(originalPaint);
        paint.setAlpha(10);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public CiPaint createRectSelectionToolPaint() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        return paint;
    }

    @Override
    public CiPaint createSelectionBoundPaint() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        return paint;
    }

    @Override
    public CiPaint createSelectionAreaPaint() {
        CiPaint paint = createSelectionBoundPaint();
        paint.setAlpha(10);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public CiPaint createResizingHandlePaint() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public CiPaint createRotationHandlePaint() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        return paint;
    }

    @Override
    public CiPaint createReferencePointPaint() {
        CiPaint paint = new CiPaint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        return paint;
    }


}
