package com.mocircle.cidrawing.persistence;

import org.json.JSONObject;

import java.util.Map;

public interface Persistable {

    JSONObject generateJson();

    Map<String, byte[]> generateResources();

    void loadFromJson(JSONObject object, Map<String, byte[]> resources);

    void afterLoaded();
}
