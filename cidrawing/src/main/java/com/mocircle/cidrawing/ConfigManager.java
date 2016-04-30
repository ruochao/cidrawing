package com.mocircle.cidrawing;

/**
 * Defines the configurations in the drawing board
 */
public interface ConfigManager {

    /**
     * Indicates the debug mode status
     *
     * @return true if it's debug mode, otherwise false.
     */
    boolean isDebugMode();

    /**
     * Sets debug mode
     *
     * @param debugMode if it's debug mode
     */
    void setDebugMode(boolean debugMode);

}
