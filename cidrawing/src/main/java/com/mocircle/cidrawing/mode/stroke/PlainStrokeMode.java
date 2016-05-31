package com.mocircle.cidrawing.mode.stroke;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;

import com.mocircle.cidrawing.core.CiPaint;

public class PlainStrokeMode extends BaseStrokeMode {

    public PlainStrokeMode() {
    }

    protected CiPaint assignPaint() {
        CiPaint paint = new CiPaint(drawingContext.getPaint());
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
