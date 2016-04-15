package com.mocircle.cidrawing;

import android.graphics.Paint;

import com.mocircle.cidrawing.mode.DrawingMode;

import java.util.ArrayList;
import java.util.List;

public class DrawingContext {

    interface DrawingModeChangedListener {

        void onDrawingModeChanged();
    }

    private String boardId;
    private DrawingMode drawingMode;
    private List<DrawingModeChangedListener> drawingModeChangedListeners = new ArrayList<>();

    private Paint paint;
    private int color;
    private int alpha;
    private float strokeWidth;

    public DrawingContext(String boardId) {
        this.boardId = boardId;
        setupDefault();
    }

    public DrawingMode getDrawingMode() {
        return drawingMode;
    }

    public void setDrawingMode(DrawingMode drawingMode) {
        if (this.drawingMode != null) {
            this.drawingMode.onLeaveMode();
        }
        this.drawingMode = drawingMode;
        this.drawingMode.setDrawingBoardId(boardId);
        this.drawingMode.onEnterMode();

        // Notify drawing mode changed
        for (DrawingModeChangedListener listener : drawingModeChangedListeners) {
            listener.onDrawingModeChanged();
        }
    }

    public void addDrawingModeChangedListener(DrawingModeChangedListener listener) {
        drawingModeChangedListeners.add(listener);
    }

    public Paint getPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        return paint;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        getPaint().setColor(color);
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        getPaint().setAlpha(alpha);
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        getPaint().setStrokeWidth(strokeWidth);
    }

    private void setupDefault() {
        getPaint().setStyle(Paint.Style.STROKE);
    }

}
