package com.mocircle.cidrawing.element.behavior;

import android.graphics.Canvas;

public interface Resizable {

    enum AspectRatioResetMethod {
        WIDTH_FIRST,
        HEIGHT_FIRST,
        SMALL_FIRST,
        LARGE_FIRST
    }

    boolean isResizingEnabled();

    void setResizingEnabled(boolean resizingEnabled);

    boolean isLockAspectRatio();

    void setLockAspectRatio(boolean lockAspectRatio);

    void resetAspectRatio(AspectRatioResetMethod method);

    void resize(float sx, float sy, float px, float py);

    void resizeTo(float width, float height, float px, float py);

    void drawResizingHandle(Canvas canvas);

    ResizingDirection hitTestForResizingHandle(float x, float y);
}
