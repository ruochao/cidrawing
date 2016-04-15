package com.mocircle.cidrawing;

public class ConfigManagerImpl implements ConfigManager {

    private boolean debugMode = false;

    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

}
