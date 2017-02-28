package com.mocircle.cidrawing.persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PersistenceManager {

    private static final String KEY_PERSISTENCE_ID = "_persistenceId";

    public static String generateResourceName() {
        return UUID.randomUUID().toString();
    }

    public static JSONObject persistObject(Persistable persistable) {
        if (persistable == null) {
            return null;
        }
        JSONObject object = persistable.generateJson();
        try {
            object.put(KEY_PERSISTENCE_ID, persistable.getClass().getName());
        } catch (JSONException e) {
            throw new PersistenceException(e);
        }
        return object;
    }

    public static JSONArray persistObjects(Collection<? extends Persistable> persistables) {
        if (persistables == null) {
            return null;
        }
        JSONArray array = new JSONArray();
        for (Persistable element : persistables) {
            array.put(persistObject(element));
        }
        return array;
    }

    public static <T extends Persistable> T buildObject(JSONObject object, Map<String, byte[]> resources) {
        String persistenceId = object.optString(KEY_PERSISTENCE_ID);
        try {
            Class clazz = Class.forName(persistenceId);
            Object instance = clazz.newInstance();
            if (instance instanceof Persistable) {
                Persistable persistable = (Persistable) instance;
                persistable.loadFromJson(object, resources);
                return (T) persistable;
            } else {
                throw new PersistenceException("Cannot create object from json");
            }
        } catch (ClassNotFoundException e) {
            throw new PersistenceException("Cannot create object from json", e);
        } catch (InstantiationException e) {
            throw new PersistenceException("Cannot create object from json", e);
        } catch (IllegalAccessException e) {
            throw new PersistenceException("Cannot create object from json", e);
        }
    }

    public static <T extends Persistable> List<T> buildObjects(JSONArray array, Map<String, byte[]> resources) throws JSONException {
        List<T> objects = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                T object = buildObject(array.getJSONObject(i), resources);
                objects.add(object);
            }
        }
        return objects;
    }

}
