package com.mocircle.cidrawing;

import android.graphics.Paint;

public interface PaintBuilder {

    Paint createDebugPaintForLine();

    Paint createDebugPaintForArea();

    Paint createPreviewPaint(Paint originalPaint);

    /**
     * Paint used for rectangle selection tool
     *
     * @return
     */
    Paint createRectSelectionToolPaint();

    /**
     * Paint used for drawing selection bounds of an element
     *
     * @return
     */
    Paint createSelectionBoundPaint();

    Paint createSelectionAreaPaint();

    Paint createResizingHandlePaint();

    Paint createRotationHandlePaint();

    Paint createReferencePointPaint();

}
