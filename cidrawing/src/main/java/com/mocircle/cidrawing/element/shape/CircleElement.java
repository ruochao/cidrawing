package com.mocircle.cidrawing.element.shape;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.utils.VectorUtils;

public class CircleElement extends OvalElement {

    @Override
    public void setupElementByVector(Vector2 vector) {
        VectorUtils.shiftVectorAsSquare(vector, true);
        super.setupElementByVector(vector);
    }

    @Override
    public boolean isLockAspectRatio() {
        return true;
    }

}
