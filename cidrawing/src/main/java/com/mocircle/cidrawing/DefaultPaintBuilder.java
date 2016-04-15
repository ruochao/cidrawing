package com.mocircle.cidrawing;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class DefaultPaintBuilder implements PaintBuilder {

    @Override
    public Paint createDebugPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }

    @Override
    public Paint createPreviewPaint(Paint originalPaint) {
        Paint paint = new Paint(originalPaint);
        paint.setAlpha(paint.getAlpha() / 2);
        return paint;
    }

    @Override
    public Paint createSelectionPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        return paint;
    }


}
