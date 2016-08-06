package com.mocircle.cidrawing;

/**
 * Defines the configurations in the drawing board
 */
public interface ConfigManager {

    /**
     * Indicate the drawing board type
     */
    enum DrawingType {

        /**
         * Vector type means all elements added to the board are being managed and independent.
         * We can choose each element from that, and do operations for each element.
         */
        Vector,

        /**
         * Painting type means all elements are drawn to the board directly, we cannot figure the
         * elements out later. It will be treat as a new canvas each time.
         */
        Painting
    }

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

    /**
     * Gets the drawing type
     *
     * @return drawing board type
     */
    DrawingType getDrawingType();

    /**
     * Sets drawing board type
     *
     * @param type drawing type
     */
    void setDrawingType(DrawingType type);

}
