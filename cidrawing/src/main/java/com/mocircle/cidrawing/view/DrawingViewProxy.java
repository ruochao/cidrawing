package com.mocircle.cidrawing.view;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * A interface delegates the view behavior.
 */
public interface DrawingViewProxy {

    /**
     * Delegates method: {@link View#onDraw(Canvas)}
     *
     * @param canvas the canvas on which the background will be drawn
     */
    void onDraw(Canvas canvas);

    /**
     * Delegates method: {@link View#onTouchEvent(MotionEvent)}
     *
     * @param event the motion event
     * @return True if the event was handled, false otherwise.
     */
    boolean onTouchEvent(MotionEvent event);

}
