package com.mocircle.cidrawing.view;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface DrawingViewProxy {

    void onDraw(Canvas canvas);

    boolean onTouchEvent(MotionEvent event);

}
