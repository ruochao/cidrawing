package com.mocircle.cidrawing.element.behavior;

import com.mocircle.cidrawing.core.Vector2;

/**
 * Indicates the element is able to be created by vector.
 */
public interface SupportVector {

    /**
     * To create the element or extract the details from a given vector.
     *
     * @param vector vector in the coordinate
     */
    void setupElementByVector(Vector2 vector);

}
