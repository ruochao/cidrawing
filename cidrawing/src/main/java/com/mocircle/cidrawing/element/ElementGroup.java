package com.mocircle.cidrawing.element;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import com.mocircle.cidrawing.persistence.PersistenceException;
import com.mocircle.cidrawing.persistence.PersistenceManager;
import com.mocircle.cidrawing.utils.ElementUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A ElementGroup is a element container basic class which contains sub elements.
 */
public abstract class ElementGroup extends DrawElement {

    private static final String KEY_ELEMENTS = "elements";

    protected transient RectF initBoundingBox;
    protected transient Path boundingPath;
    protected List<DrawElement> elements;

    public ElementGroup() {
    }

    public ElementGroup(List<DrawElement> elements) {
        setElements(elements);
    }

    public List<DrawElement> getElements() {
        return elements;
    }

    public void setElements(List<DrawElement> elements) {
        this.elements = elements;
        // Make sure the element's order is the same in layer
        ElementUtils.sortElementsInLayer(elements);
        recalculateBoundingBox();
    }

    @Override
    public JSONObject generateJson() {
        JSONObject object = super.generateJson();
        try {
            object.put(KEY_ELEMENTS, PersistenceManager.persistObjects(elements));
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public Map<String, byte[]> generateResources() {
        Map<String, byte[]> resMap = new HashMap<>();
        for (DrawElement element : elements) {
            Map<String, byte[]> map = element.generateResources();
            if (map != null) {
                resMap.putAll(map);
            }
        }
        return resMap;
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        super.loadFromJson(object, resources);
        if (object != null) {
            try {
                JSONArray array = object.getJSONArray(KEY_ELEMENTS);
                elements = PersistenceManager.buildObjects(array, resources);
            } catch (JSONException e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Override
    public void afterLoaded() {
        for (DrawElement element : elements) {
            element.afterLoaded();
        }
        recalculateBoundingBox();
    }

    @Override
    public void applyMatrixForData(Matrix matrix) {
        super.applyMatrixForData(matrix);

        boundingPath = new Path();
        boundingPath.addRect(initBoundingBox, Path.Direction.CW);
        boundingPath.transform(dataMatrix);
    }

    @Override
    public Matrix applyDisplayMatrixToData() {
        Matrix matrix = new Matrix(getDisplayMatrix());
        applyMatrixForData(getDisplayMatrix());
        getDisplayMatrix().reset();
        recalculateBoundingBox();
        return matrix;
    }

    @Override
    public void restoreDisplayMatrixFromData(Matrix matrix) {
        if (matrix != null) {
            Matrix invertDisplayMatrix = new Matrix();
            matrix.invert(invertDisplayMatrix);
            applyMatrixForData(invertDisplayMatrix);
            getDisplayMatrix().set(new Matrix(matrix));
            recalculateBoundingBox();
        }
    }

    @Override
    public void updateBoundingBox() {
        if (boundingPath != null) {
            RectF box = new RectF();
            boundingPath.computeBounds(box, true);
            setBoundingBox(box);
        }
    }

    @Override
    public boolean isMovementEnabled() {
        boolean result = true;
        for (DrawElement element : elements) {
            result &= element.isMovementEnabled();
        }
        return result;
    }

    @Override
    public boolean isRotationEnabled() {
        boolean result = true;
        for (DrawElement element : elements) {
            result &= element.isRotationEnabled();
        }
        return result;
    }

    @Override
    public boolean isResizingEnabled() {
        boolean result = true;
        for (DrawElement element : elements) {
            result &= element.isResizingEnabled();
        }
        return result;
    }

    @Override
    public boolean isLockAspectRatio() {
        boolean result = false;
        for (DrawElement element : elements) {
            result |= element.isLockAspectRatio();
        }
        return result;
    }

    @Override
    public boolean isSkewEnabled() {
        boolean result = true;
        for (DrawElement element : elements) {
            result &= element.isSkewEnabled();
        }
        return result;
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof ElementGroup) {
            ElementGroup obj = (ElementGroup) element;
            if (initBoundingBox != null) {
                obj.initBoundingBox = new RectF(initBoundingBox);
            }
            if (boundingPath != null) {
                obj.boundingPath = new Path(boundingPath);
            }
            if (elements != null) {
                obj.elements = new ArrayList<>();
                for (DrawElement e : elements) {
                    obj.elements.add((DrawElement) e.clone());
                }
            }
        }
    }

    protected void recalculateBoundingBox() {
        RectF box = new RectF();
        for (DrawElement element : elements) {
            box.union(element.getOuterBoundingBox());
        }

        initBoundingBox = new RectF(box);
        boundingPath = new Path();
        boundingPath.addRect(box, Path.Direction.CW);
        updateBoundingBox();
    }

}
