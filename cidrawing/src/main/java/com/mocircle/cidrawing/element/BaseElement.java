package com.mocircle.cidrawing.element;

import com.mocircle.cidrawing.persistence.Persistable;
import com.mocircle.cidrawing.persistence.PersistenceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseElement implements Serializable, Cloneable, Persistable {

    private static final String KEY_NAME = "name";

    protected String name;

    public BaseElement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject generateJson() {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_NAME, name);
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    @Override
    public Map<String, byte[]> generateResources() {
        return new HashMap<>();
    }

    @Override
    public void loadFromJson(JSONObject object, Map<String, byte[]> resources) {
        if (object != null) {
            name = object.optString(KEY_NAME);
        }
    }

    @Override
    public void afterLoaded() {
    }

    @Override
    public abstract Object clone();

    protected void cloneTo(BaseElement element) {
        element.name = name;
    }

}
