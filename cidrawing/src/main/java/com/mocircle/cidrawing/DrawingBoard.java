package com.mocircle.cidrawing;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.operation.OperationManager;
import com.mocircle.cidrawing.persistence.ExportData;
import com.mocircle.cidrawing.view.DrawingView;

import org.json.JSONObject;

import java.util.Map;

/**
 * Drawing board is a main interface to manage the drawing.
 */
public interface DrawingBoard {

    /**
     * Gets the unique id of the board.
     *
     * @return board id
     */
    String getBoardId();

    /**
     * Connects drawing view with manager classes. It should be called at the beginning before using
     * view.
     *
     * @param view drawing view implementation
     */
    void setupDrawingView(DrawingView view);

    /**
     * Gets the drawing view object
     *
     * @return drawing view
     */
    DrawingView getDrawingView();

    /**
     * Gets the drawing context object.
     *
     * @return drawing context
     */
    DrawingContext getDrawingContext();

    /**
     * Gets element manager.
     *
     * @return element manager
     */
    ElementManager getElementManager();

    /**
     * Gets operation manager.
     *
     * @return operation manager
     */
    OperationManager getOperationManager();

    /**
     * Gets configuration manager.
     *
     * @return configuration manager
     */
    ConfigManager getConfigManager();

    /**
     * Gets paint builder.
     *
     * @return paint builder
     */
    PaintBuilder getPaintBuilder();

    /**
     * Replaces the paint builder used in the drawing board.
     *
     * @param paintBuilder paint builder
     */
    void setPaintBuilder(PaintBuilder paintBuilder);

    /**
     * Gets painting behavior instance.
     *
     * @return painting behavior
     */
    PaintingBehavior getPaintingBehavior();

    /**
     * Replaces the painting behavior implementation used in the drawing board.
     *
     * @param paintingBehavior painting behavior
     */
    void setPaintingBehavior(PaintingBehavior paintingBehavior);

    /**
     * Export drawing board content
     *
     * @return json data and external resources
     */
    ExportData exportData();

    /**
     * Load drawing board
     *
     * @param metaData  json format meta data
     * @param resources resource mapping
     */
    void importData(JSONObject metaData, Map<String, byte[]> resources);

}
