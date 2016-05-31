package com.mocircle.cidrawing.mode.stroke;

import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;

import com.mocircle.cidrawing.core.CiPaint;

public class SmoothStrokeMode extends BaseStrokeMode {

    protected int smoothRadius = 100;

    public SmoothStrokeMode() {
    }

    public SmoothStrokeMode(int smoothRadius) {
        this.smoothRadius = smoothRadius;
    }

    public int getSmoothRadius() {
        return smoothRadius;
    }

    public void setSmoothRadius(int smoothRadius) {
        this.smoothRadius = smoothRadius;
    }

    protected CiPaint assignPaint() {
        CiPaint paint = new CiPaint(drawingContext.getPaint());
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        if (smoothRadius > 0) {
            CornerPathEffect effect = new CornerPathEffect(smoothRadius);
            if (paint.getPathEffect() == null) {
                paint.setPathEffect(effect);
            } else {
                ComposePathEffect composeEffect = new ComposePathEffect(paint.getPathEffect(), effect);
                paint.setPathEffect(composeEffect);
            }
        }
        return paint;
    }
}
