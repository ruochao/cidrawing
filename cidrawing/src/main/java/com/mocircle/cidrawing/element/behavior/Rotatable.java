package com.mocircle.cidrawing.element.behavior;

import android.graphics.Canvas;

public interface Rotatable {

    boolean isRotationEnabled();

    void setRotationEnabled(boolean rotationEnabled);

    void rotate(float degree, float px, float py);

    void rotateTo(float degree, float px, float py);

    /**
     * Get the rotated angle, from -180 to 180
     *
     * @return angle
     */
    float getAngle();

    void drawRotationHandle(Canvas canvas);

    boolean hitTestForRotationHandle(float x, float y);
}
