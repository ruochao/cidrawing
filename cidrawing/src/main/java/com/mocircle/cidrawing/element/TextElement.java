package com.mocircle.cidrawing.element;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.behavior.SupportVector;
import com.mocircle.cidrawing.persistence.PersistenceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class TextElement extends BoundsElement implements SupportVector {

    private static final String KEY_TEXT = "text";
    private static final String KEY_SIZE = "textSize";

    protected String text = "";
    protected float textSize;

    public TextElement() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        calculateBoundingBox();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        setupPaint();
    }

    @Override
    public JSONObject generateJson() {
        JSONObject object = super.generateJson();
        try {
            object.put(KEY_TEXT, text);
            object.put(KEY_SIZE, textSize);
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        super.loadFromJson(object, resources);
        if (object != null) {
            text = object.optString(KEY_TEXT, "");
            textSize = (float) object.optDouble(KEY_SIZE);
        }
    }

    @Override
    public void afterLoaded() {
        setupPaint();
        calculateBoundingBox();
    }

    @Override
    public void setPaint(CiPaint paint) {
        super.setPaint(paint);
        setupPaint();
        calculateBoundingBox();
    }

    @Override
    public void setupElementByVector(Vector2 vector) {
        RectF box = vector.getRect();
        move(box.left, box.top);
    }

    @Override
    public void drawElement(Canvas canvas) {
        canvas.save();
        canvas.concat(dataMatrix);
        canvas.drawText(text, 0, 0, paint);
        canvas.restore();
    }

    @Override
    public Object clone() {
        TextElement element = new TextElement();
        cloneTo(element);
        return element;
    }

    @Override
    protected void calculateBoundingBox() {
        Rect box = new Rect();
        paint.getTextBounds(text, 0, text.length(), box);
        originalBoundingBox = new RectF(box);
        boundingPath = new Path();
        boundingPath.addRect(originalBoundingBox, Path.Direction.CW);
        boundingPath.transform(dataMatrix);
        updateBoundingBox();
    }

    @Override
    protected void cloneTo(BaseElement element) {
        super.cloneTo(element);
        if (element instanceof TextElement) {
            TextElement obj = (TextElement) element;
            obj.text = text;
            obj.textSize = textSize;
        }
    }

    private void setupPaint() {
        paint.setTextSize(textSize);
    }

}
