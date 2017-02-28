package com.mocircle.cidrawing.persistence;

import org.json.JSONObject;

import java.util.Map;

public class ExportData {

    private JSONObject metaData;
    private Map<String, byte[]> resources;

    public JSONObject getMetaData() {
        return metaData;
    }

    public void setMetaData(JSONObject metaData) {
        this.metaData = metaData;
    }

    public Map<String, byte[]> getResources() {
        return resources;
    }

    public void setResources(Map<String, byte[]> resources) {
        this.resources = resources;
    }
}
