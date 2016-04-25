package com.mocircle.cidrawing.element.behavior;

public interface Movable {

    boolean isMovementEnabled();

    void setMovementEnabled(boolean movementEnabled);

    void move(float dx, float dy);

    void moveTo(float locX, float locY);

    float getLocX();

    float getLocY();
}
