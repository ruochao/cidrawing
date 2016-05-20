package com.mocircle.cidrawing;

import com.mocircle.cidrawing.core.CiPaint;

/**
 * A interface defines all kinds of paint. Each method will return a new paint object.
 */
public interface PaintBuilder {

    /**
     * Creates a new paint for drawing auxiliary line.
     *
     * @return the paint object created
     */
    CiPaint createDebugPaintForLine();

    /**
     * Creates a new paint for drawing auxiliary area.
     *
     * @return the paint object created
     */
    CiPaint createDebugPaintForArea();

    /**
     * Creates a paint for drawing element preview look.
     *
     * @param originalPaint original paint of the element
     * @return the paint object created
     */
    CiPaint createPreviewPaint(CiPaint originalPaint);

    /**
     * Creates a paint for drawing element preview look with fill effect.
     *
     * @param originalPaint original paint of the element
     * @return the paint object created
     */
    CiPaint createPreviewAreaPaint(CiPaint originalPaint);

    /**
     * Creates a paint used for rectangle selection tool.
     *
     * @return the paint object created
     */
    CiPaint createRectSelectionToolPaint();

    /**
     * Creates a paint for drawing selection bounds of an element.
     *
     * @return the paint object created
     */
    CiPaint createSelectionBoundPaint();

    /**
     * Creates a paint for drawing multiple selection inner area.
     *
     * @return the paint object created
     */
    CiPaint createSelectionAreaPaint();

    /**
     * Creates a paint for drawing resizing handle.
     *
     * @return the paint object created
     */
    CiPaint createResizingHandlePaint();

    /**
     * Creates a paint for drawing rotation handle.
     *
     * @return the paint object created
     */
    CiPaint createRotationHandlePaint();

    /**
     * Creates a paint for drawing reference point.
     *
     * @return the paint object created
     */
    CiPaint createReferencePointPaint();

}
