package com.mocircle.cidrawing.element;

import com.mocircle.cidrawing.core.Vector2;

/**
 * A kind of element which is able to be created by vector.
 */
public abstract class VectorElement extends DrawElement {

    /**
     * To create the element or extract the details from a given vector.
     *
     * @param vector vector in the coordinate
     */
    public abstract void setupElementByVector(Vector2 vector);

}
