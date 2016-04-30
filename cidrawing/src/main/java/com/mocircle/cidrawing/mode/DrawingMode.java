package com.mocircle.cidrawing.mode;

import android.view.MotionEvent;

/**
 * A drawing mode defines the behavior when interacting with the view.
 */
public interface DrawingMode {

    /**
     * Sets drawing board id
     *
     * @param boardId board id
     */
    void setDrawingBoardId(String boardId);

    /**
     * Called when switch to this mode.
     */
    void onEnterMode();

    /**
     * Called when switch to other mode.
     */
    void onLeaveMode();

    /**
     * Delegates the view touch event.
     *
     * @param event the motion event
     * @return True if the event was handled, false otherwise.
     */
    boolean onTouchEvent(MotionEvent event);

}
