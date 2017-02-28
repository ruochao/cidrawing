package com.mocircle.cidrawing.element.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BasePathElement;
import com.mocircle.cidrawing.element.behavior.SupportVector;

import org.json.JSONObject;

public abstract class ShapeElement extends BasePathElement implements SupportVector {

    public ShapeElement() {
    }

    @Override
    public void afterLoaded() {
        elementPath = createShapePath();
        updateBoundingBox();
    }

    @Override
    public void drawElement(Canvas canvas) {
        if (elementPath != null) {
            canvas.drawPath(elementPath, paint);
            if (paint.getStyle() == Paint.Style.FILL_AND_STROKE) {
                Integer originalColor = null;
                if (paint.getSecondaryColor() != null) {
                    originalColor = paint.getColor();
                    paint.setColor(paint.getSecondaryColor());
                }
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(elementPath, paint);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (originalColor != null) {
                    paint.setColor(originalColor);
                }
            }
        }
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        retrieveAttributesFromVector(vector);
        elementPath = createShapePath();
        updateBoundingBox();
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        elementPath = createShapePath();
        elementPath.transform(dataMatrix);
    }

    protected abstract void retrieveAttributesFromVector(Vector2 vector);

    protected abstract Path createShapePath();

}
