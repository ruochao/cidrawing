package com.mocircle.cidrawing.element.shape;

import android.graphics.RectF;

import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.BaseElement;
import com.mocircle.cidrawing.persistence.ConvertUtils;
import com.mocircle.cidrawing.persistence.PersistenceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * A kind of shape which is able to be drawn correctly with a given box.
 */
public abstract class BoxShapeElement extends ShapeElement {

    private static final String KEY_SHAPE_VECTOR = "shapeVector";
    private static final String KEY_SHAPE_BOX = "shapeBox";

    protected Vector2 shapeVector;
    protected RectF shapeBox = new RectF();

    @Override
    public JSONObject generateJson() {
        JSONObject object = super.generateJson();
        try {
            object.put(KEY_SHAPE_VECTOR, ConvertUtils.vectorToJson(shapeVector));
            object.put(KEY_SHAPE_BOX, ConvertUtils.rectToJson(shapeBox));
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        super.loadFromJson(object, resources);
        if (object != null) {
            try {
                shapeVector = ConvertUtils.vectorFromJson(object.optJSONArray(KEY_SHAPE_VECTOR));
                shapeBox = ConvertUtils.rectFromJson(object.optJSONArray(KEY_SHAPE_BOX));
            } catch (JSONException e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Override
    protected void retrieveAttributesFromVector(Vector2 vector) {
        shapeVector = vector;
        shapeBox = vector.getRect();
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof BoxShapeElement) {
            BoxShapeElement obj = (BoxShapeElement) element;
            if (shapeVector != null) {
                obj.shapeVector = new Vector2(shapeVector.getPoint1(), shapeVector.getPoint2());
            }
            obj.shapeBox = new RectF(shapeBox);
        }
    }
}
