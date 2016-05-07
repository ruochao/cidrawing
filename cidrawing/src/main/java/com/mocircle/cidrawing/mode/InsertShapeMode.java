package com.mocircle.cidrawing.mode;

import android.graphics.Paint;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.VectorElement;
import com.mocircle.cidrawing.element.shape.ShapeElement;

public class InsertShapeMode extends InsertVectorElementMode {

    private static final String TAG = "InsertShapeMode";

    private Class<? extends ShapeElement> shapeType;

    public InsertShapeMode() {
    }

    public void setShapeType(Class<? extends ShapeElement> shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    protected VectorElement createPreviewElement() {
        previewElement = getShapeInstance();
        previewElement.setPaint(paintBuilder.createPreviewPaint(drawingContext.getPaint()));
        return previewElement;
    }

    @Override
    protected VectorElement createRealElement(Vector2 vector) {
        ShapeElement element = getShapeInstance();
        element.setPaint(new Paint(drawingContext.getPaint()));
        element.setupElementByVector(vector);
        return element;
    }

    private ShapeElement getShapeInstance() {
        try {
            return shapeType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

}
