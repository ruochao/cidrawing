package com.mocircle.cidrawing;

import android.graphics.Paint;

public interface PaintBuilder {

    Paint createDebugPaint();

    Paint createPreviewPaint(Paint originalPaint);

    Paint createSelectionPaint();

}
