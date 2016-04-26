package com.mocircle.cidrawing.utils;

import android.graphics.Matrix;


public class MatrixUtils {

    public static Matrix getInvertMatrix(Matrix matrix) {
        Matrix invertMatrix = new Matrix();
        matrix.invert(invertMatrix);
        return invertMatrix;
    }

    /**
     * M(delta) * M(source) = M(target)
     *
     * @param source
     * @param target
     * @return
     */
    public static Matrix getDeltaMatrix(Matrix source, Matrix target) {
        Matrix delta = new Matrix();
        source.invert(delta);
        delta.postConcat(target);
        return delta;
    }
}
