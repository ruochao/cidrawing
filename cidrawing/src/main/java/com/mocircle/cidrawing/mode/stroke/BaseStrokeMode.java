package com.mocircle.cidrawing.mode.stroke;

import com.mocircle.cidrawing.DrawingContext;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.element.StrokeElement;
import com.mocircle.cidrawing.mode.BasePointMode;
import com.mocircle.cidrawing.operation.InsertElementOperation;
import com.mocircle.cidrawing.operation.OperationManager;

public abstract class BaseStrokeMode extends BasePointMode {

    protected ElementManager elementManager;
    protected OperationManager operationManager;
    protected DrawingContext drawingContext;

    protected StrokeElement element;
    protected boolean immutable;

    public BaseStrokeMode() {
    }

    public boolean getStrokeImmutable() {
        return immutable;
    }

    public void setStrokeImmutable(boolean immutable) {
        this.immutable = immutable;
        if (element != null) {
            element.setSelectionEnabled(!immutable);
        }
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        operationManager = drawingBoard.getOperationManager();
        drawingContext = drawingBoard.getDrawingContext();
    }

    @Override
    protected void onFirstPointDown(float x, float y) {
        element = new StrokeElement();
        element.setSelectionEnabled(!immutable);
        element.setPaint(assignPaint());
        elementManager.addElementToCurrentLayer(element);
        element.addPoint(x, y);
    }

    @Override
    protected void onOverPoint(float x, float y) {
        element.addPoint(x, y);
    }

    @Override
    protected void onLastPointUp(float x, float y, boolean singleTap) {
        element.addPoint(x, y);
        element.doneEditing();
        elementManager.removeElementFromCurrentLayer(element);
        operationManager.executeOperation(new InsertElementOperation(element));
    }

    @Override
    protected void onPointCancelled() {
        elementManager.removeElementFromCurrentLayer(element);
    }

    protected abstract CiPaint assignPaint();
}
