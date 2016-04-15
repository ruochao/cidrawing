package com.mocircle.cidrawing.element.behavior;

import android.graphics.Canvas;

import com.mocircle.cidrawing.mode.ResizingDirection;

public interface Resizable {

    boolean isResizingEnabled();

    void setResizingEnabled(boolean resizingEnabled);

    void resize(float sx, float sy, float px, float py);

    void resizeTo(float sx, float sy, float px, float py);

    void drawResizingHandle(Canvas canvas);

    ResizingDirection hitTestForResizingHandle(float x, float y);
}
