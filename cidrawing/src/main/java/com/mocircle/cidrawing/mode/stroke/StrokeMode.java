package com.mocircle.cidrawing.mode.stroke;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.cidrawing.DrawingContext;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.element.StrokeElement;
import com.mocircle.cidrawing.mode.AbstractDrawingMode;
import com.mocircle.cidrawing.operation.InsertElementOperation;
import com.mocircle.cidrawing.operation.OperationManager;

public class StrokeMode extends AbstractDrawingMode {

    private ElementManager elementManager;
    private OperationManager operationManager;
    private DrawingContext drawingContext;

    private StrokeElement element;

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        operationManager = drawingBoard.getOperationManager();
        drawingContext = drawingBoard.getDrawingContext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                element = new StrokeElement();
                element.setPaint(assignPaint());
                elementManager.addElementToCurrentLayer(element);
                element.addPoint(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                element.addPoint(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                element.addPoint(event.getX(), event.getY());
                element.doneEditing();
                elementManager.removeElementFromCurrentLayer(element);
                operationManager.executeOperation(new InsertElementOperation(element));
                return true;
            case MotionEvent.ACTION_CANCEL:
                elementManager.removeElementFromCurrentLayer(element);
                return true;
        }
        return false;
    }

    protected CiPaint assignPaint() {
        CiPaint paint = new CiPaint(drawingContext.getPaint());
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
