package com.mocircle.cidrawing.view;

import android.content.Context;

/**
 * Drawing view interface
 */
public interface DrawingView {

    /**
     * Sets the proxy object to delegate view behaviors.
     *
     * @param viewProxy proxy object
     */
    void setViewProxy(DrawingViewProxy viewProxy);

    /**
     * Notifies to redraw the view
     */
    void notifyViewUpdated();

    /**
     * Gets android context
     *
     * @return android context
     */
    Context getContext();

}
