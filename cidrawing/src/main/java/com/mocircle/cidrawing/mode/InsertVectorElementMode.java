package com.mocircle.cidrawing.mode;

import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.DrawingContext;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.behavior.SupportVector;
import com.mocircle.cidrawing.element.shape.RectElement;
import com.mocircle.cidrawing.operation.InsertElementOperation;
import com.mocircle.cidrawing.operation.OperationManager;

public class InsertVectorElementMode extends AbstractDrawingMode {

    private static final String TAG = "InsertVectorElementMode";

    protected ElementManager elementManager;
    protected DrawingContext drawingContext;
    protected OperationManager operationManager;
    protected PaintBuilder paintBuilder;

    protected float downX;
    protected float downY;

    protected DrawElement previewElement;
    protected DrawElement realElement;

    public InsertVectorElementMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        drawingContext = drawingBoard.getDrawingContext();
        operationManager = drawingBoard.getOperationManager();
        paintBuilder = drawingBoard.getPaintBuilder();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                CircleLog.d(TAG, "Touch Down: x=" + downX + ", y=" + downY);

                previewElement = createPreviewElement();
                elementManager.addElementToCurrentLayer(previewElement);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (previewElement != null) {
                    ((SupportVector) previewElement).setupElementByVector(new Vector2(downX, downY, event.getX(), event.getY()));
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (previewElement != null) {
                    elementManager.removeElementFromCurrentLayer(previewElement);

                    DrawElement element = createRealElement(new Vector2(downX, downY, event.getX(), event.getY()));
                    operationManager.executeOperation(new InsertElementOperation(element));
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                elementManager.removeElementFromCurrentLayer(previewElement);
                return true;
        }
        return false;
    }

    protected void setVectorElement(DrawElement realElement) {
        this.realElement = realElement;
        if (!(realElement instanceof SupportVector)) {
            throw new IllegalArgumentException("Element must implement CreateByVector interface.");
        }
    }

    protected DrawElement createPreviewElement() {
        previewElement = new RectElement();
        previewElement.setPaint(paintBuilder.createPreviewAreaPaint(drawingContext.getPaint()));
        return previewElement;
    }

    protected DrawElement createRealElement(Vector2 vector) {
        DrawElement element = (DrawElement) realElement.clone();
        element.setPaint(new CiPaint(drawingContext.getPaint()));
        ((SupportVector) element).setupElementByVector(vector);
        return element;
    }

}
