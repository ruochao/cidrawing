package com.mocircle.cidrawing.core;

import android.graphics.Paint;

public class CiPaint extends Paint {

    private Integer secondaryColor;

    public CiPaint() {
        super();
    }

    public CiPaint(int flags) {
        super(flags);
    }

    public CiPaint(Paint paint) {
        super(paint);
        if (paint instanceof CiPaint) {
            CiPaint p = (CiPaint) paint;
            secondaryColor = p.secondaryColor;
        }
    }

    public Integer getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Integer color) {
        secondaryColor = color;
    }

}
