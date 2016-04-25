package com.mocircle.cidrawing.element.behavior;

public interface Skewable {

    boolean isSkewEnabled();

    void setSkewEnabled(boolean skewEnabled);

    void skew(float kx, float ky, float px, float py);

}
