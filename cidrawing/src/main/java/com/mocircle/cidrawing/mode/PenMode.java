package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.cidrawing.DrawingContext;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.operation.OperationManager;
import com.mocircle.cidrawing.operation.InsertElementOperation;
import com.mocircle.cidrawing.element.StrokeElement;

public class PenMode extends AbstractDrawingMode {

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
                element.setPaint(new Paint(drawingContext.getPaint()));
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
}
