package com.mocircle.cidrawing;

import android.graphics.Paint;

/**
 * A interface defines all kinds of paint. Each method will return a new paint object.
 */
public interface PaintBuilder {

    /**
     * Creates a new paint for drawing auxiliary line.
     *
     * @return the paint object created
     */
    Paint createDebugPaintForLine();

    /**
     * Creates a new paint for drawing auxiliary area.
     *
     * @return the paint object created
     */
    Paint createDebugPaintForArea();

    /**
     * Creates a paint for drawing element preview look.
     *
     * @param originalPaint original paint of the element
     * @return the paint object created
     */
    Paint createPreviewPaint(Paint originalPaint);

    /**
     * Creates a paint for drawing element preview look with fill effect.
     *
     * @param originalPaint original paint of the element
     * @return the paint object created
     */
    Paint createPreviewAreaPaint(Paint originalPaint);

    /**
     * Creates a paint used for rectangle selection tool.
     *
     * @return the paint object created
     */
    Paint createRectSelectionToolPaint();

    /**
     * Creates a paint for drawing selection bounds of an element.
     *
     * @return the paint object created
     */
    Paint createSelectionBoundPaint();

    /**
     * Creates a paint for drawing multiple selection inner area.
     *
     * @return the paint object created
     */
    Paint createSelectionAreaPaint();

    /**
     * Creates a paint for drawing resizing handle.
     *
     * @return the paint object created
     */
    Paint createResizingHandlePaint();

    /**
     * Creates a paint for drawing rotation handle.
     *
     * @return the paint object created
     */
    Paint createRotationHandlePaint();

    /**
     * Creates a paint for drawing reference point.
     *
     * @return the paint object created
     */
    Paint createReferencePointPaint();

}
