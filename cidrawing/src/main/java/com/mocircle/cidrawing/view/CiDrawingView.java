package com.mocircle.cidrawing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A view to provide drawing features.
 */
public class CiDrawingView extends View implements DrawingView {

    protected DrawingViewProxy viewProxy;

    public CiDrawingView(Context context) {
        super(context);
        setupView();
    }

    public CiDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public CiDrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CiDrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView();
    }

    @Override
    public void setViewProxy(DrawingViewProxy viewProxy) {
        this.viewProxy = viewProxy;
    }

    @Override
    public void notifyViewUpdated() {
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (viewProxy != null) {
            if (viewProxy.onTouchEvent(event)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (viewProxy != null) {
            viewProxy.onDraw(canvas);
        }
    }

    protected void setupView() {
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

}
