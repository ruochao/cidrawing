package com.mocircle.cidrawing;

import android.graphics.Paint;

public interface PaintBuilder {

    Paint createDebugPaintForLine();

    Paint createDebugPaintForArea();

    Paint createPreviewPaint(Paint originalPaint);

    Paint createSelectionPaint();

}
