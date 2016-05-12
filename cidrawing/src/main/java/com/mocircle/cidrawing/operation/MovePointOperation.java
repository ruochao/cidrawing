package com.mocircle.cidrawing.operation;

import android.graphics.PointF;

public class MovePointOperation extends AbstractOperation {

    private PointF point;
    private float deltaX;
    private float deltaY;

    public MovePointOperation(PointF point, float deltaX, float deltaY) {
        this.point = point;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public boolean isExecutable() {
        return point != null;
    }

    @Override
    public boolean doOperation() {
        point.offset(deltaX, deltaY);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        point.offset(-deltaX, -deltaY);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }
}
