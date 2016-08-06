package com.mocircle.cidrawing;

/**
 * The default implementation of {@link ConfigManager}.
 */
public class ConfigManagerImpl implements ConfigManager {

    private boolean debugMode = false;
    private DrawingType drawingType = DrawingType.Vector;

    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public DrawingType getDrawingType() {
        return drawingType;
    }

    @Override
    public void setDrawingType(DrawingType type) {
        this.drawingType = type;
    }

}
