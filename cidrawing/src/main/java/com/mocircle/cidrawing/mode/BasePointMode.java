package com.mocircle.cidrawing.mode;

import android.view.MotionEvent;

import com.mocircle.cidrawing.utils.DrawUtils;

public class BasePointMode extends AbstractDrawingMode {

    protected float downX;
    protected float downY;

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                onFirstPointDown(downX, downY);
                return true;
            case MotionEvent.ACTION_MOVE:
                onOverPoint(event.getX(), event.getY());
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                boolean singleTap = DrawUtils.isSingleTap(drawingBoard.getDrawingView().getContext(), downX, downY, event);
                onLastPointUp(event.getX(), event.getY(), singleTap);
                return true;
            case MotionEvent.ACTION_CANCEL:
                onPointCancelled();
                return true;
        }
        return false;
    }

    protected void onFirstPointDown(float x, float y) {
    }

    protected void onOverPoint(float x, float y) {
    }

    protected void onLastPointUp(float x, float y, boolean singleTap) {
    }

    protected void onPointCancelled() {
    }

}