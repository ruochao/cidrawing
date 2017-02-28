package com.mocircle.cidrawing.core;

import android.graphics.Paint;

import com.mocircle.cidrawing.persistence.Persistable;
import com.mocircle.cidrawing.persistence.PersistenceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CiPaint extends Paint implements Persistable {

    private static final String KEY_COLOR = "color";
    private static final String KEY_STROKE_WIDTH = "strokeWidth";
    private static final String KEY_FLAGS = "flags";
    private static final String KEY_ALPHA = "alpha";
    private static final String KEY_STYLE = "style";
    private static final String KEY_SECONDARY_COLOR = "secondaryColor";

    private Integer secondaryColor;

    public CiPaint() {
        super();
    }

    public CiPaint(int flags) {
        super(flags);
    }

    public CiPaint(Paint paint) {
        super(paint);
        if (paint instanceof CiPaint) {
            CiPaint p = (CiPaint) paint;
            secondaryColor = p.secondaryColor;
        }
    }

    public Integer getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Integer color) {
        secondaryColor = color;
    }

    @Override
    public JSONObject generateJson() {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_COLOR, this.getColor());
            object.put(KEY_STROKE_WIDTH, this.getStrokeWidth());
            object.put(KEY_FLAGS, this.getFlags());
            object.put(KEY_ALPHA, this.getAlpha());
            object.put(KEY_STYLE, this.getStyle().toString());
            object.put(KEY_SECONDARY_COLOR, this.getSecondaryColor());
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public Map<String, byte[]> generateResources() {
        return null;
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        if (object != null) {
            try {
                setColor(object.getInt(KEY_COLOR));
                setStrokeWidth((float) object.getDouble(KEY_STROKE_WIDTH));
                setFlags(object.getInt(KEY_FLAGS));
                setAlpha(object.getInt(KEY_ALPHA));
                setStyle(Style.valueOf(object.getString(KEY_STYLE)));
                if (object.has(KEY_SECONDARY_COLOR)) {
                    secondaryColor = object.getInt(KEY_SECONDARY_COLOR);
                }
            } catch (JSONException e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Override
    public void afterLoaded() {
    }
}
