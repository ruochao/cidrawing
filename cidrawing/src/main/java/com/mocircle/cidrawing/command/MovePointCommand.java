package com.mocircle.cidrawing.command;

import android.graphics.PointF;

public class MovePointCommand extends AbstractCommand {

    private PointF point;
    private float deltaX;
    private float deltaY;

    public MovePointCommand(PointF point, float deltaX, float deltaY) {
        this.point = point;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public boolean isExecutable() {
        return point != null;
    }

    @Override
    public boolean doCommand() {
        point.offset(deltaX, deltaY);
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undoCommand() {
        point.offset(-deltaX, -deltaY);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }
}
