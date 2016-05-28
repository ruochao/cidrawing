package com.mocircle.cidrawing.mode;

import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.shape.ShapeElement;

public class InsertShapeMode extends InsertVectorElementMode {

    private static final String TAG = "InsertShapeMode";

    private Class<? extends ShapeElement> shapeType;
    private ShapeElement shapeInstance;

    public InsertShapeMode() {
    }

    /**
     * Sets shape type, it has the lower priority then {@link #setShapeInstance(ShapeElement)},
     * which means if shape instance has been set, it will ignore shape type.
     *
     * @param shapeType shape type
     */
    public void setShapeType(Class<? extends ShapeElement> shapeType) {
        this.shapeType = shapeType;
    }

    /**
     * Sets shape instance, it will create shape according to this sample instance. And it has the
     * higher priority then {@link #setShapeType(Class)}, which means if we set shape type and shape
     * instance, shape instance will take effect.
     *
     * @param shapeInstance shape instance
     */
    public void setShapeInstance(ShapeElement shapeInstance) {
        this.shapeInstance = shapeInstance;
    }

    @Override
    protected DrawElement createPreviewElement() {
        previewElement = getShapeInstance();
        previewElement.setPaint(paintBuilder.createPreviewPaint(drawingContext.getPaint()));
        return previewElement;
    }

    @Override
    protected DrawElement createRealElement(Vector2 vector) {
        ShapeElement element = getShapeInstance();
        element.setPaint(new CiPaint(drawingContext.getPaint()));
        element.setupElementByVector(vector);
        return element;
    }

    private ShapeElement getShapeInstance() {
        if (shapeInstance != null) {
            return (ShapeElement) shapeInstance.clone();
        } else if (shapeType != null) {
            try {
                return shapeType.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create shape.", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create shape.", e);
            }
        } else {
            throw new RuntimeException("Cannot find shape type or shape sample instance to create the new shape.");
        }
    }

}
