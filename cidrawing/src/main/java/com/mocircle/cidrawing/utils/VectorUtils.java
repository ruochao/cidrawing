package com.mocircle.cidrawing.utils;

import com.mocircle.cidrawing.core.Vector2;

public final class VectorUtils {

    private VectorUtils() {
    }

    /**
     * Change the vector to make two points of vector create a square based on point1.
     *
     * @param vector       the given vector
     * @param smallerFirst true to create a smaller square, otherwise bigger square.
     */
    public static void shiftVectorAsSquare(Vector2 vector, boolean smallerFirst) {
        float deltaX = 0, deltaY = 0;
        float delta = Math.abs(Math.abs(vector.getValueX()) - Math.abs(vector.getValueY()));
        if (smallerFirst) {
            if (Math.abs(vector.getValueX()) < Math.abs(vector.getValueY())) {
                deltaY = vector.getValueY() > 0 ? -delta : delta;
            } else {
                deltaX = vector.getValueX() > 0 ? -delta : delta;
            }
        } else {
            if (Math.abs(vector.getValueX()) > Math.abs(vector.getValueY())) {
                deltaY = vector.getValueY() > 0 ? delta : -delta;
            } else {
                deltaX = vector.getValueX() > 0 ? delta : -delta;
            }
        }
        vector.getPoint2().x += deltaX;
        vector.getPoint2().y += deltaY;
    }

}
