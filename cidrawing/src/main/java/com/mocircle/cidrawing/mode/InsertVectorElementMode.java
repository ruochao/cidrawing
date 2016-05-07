package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.DrawingContext;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.command.CommandManager;
import com.mocircle.cidrawing.command.InsertElementCommand;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.VectorElement;
import com.mocircle.cidrawing.element.shape.RectElement;

public class InsertVectorElementMode extends AbstractDrawingMode {

    private static final String TAG = "InsertVectorElementMode";

    protected ElementManager elementManager;
    protected DrawingContext drawingContext;
    protected CommandManager commandManager;
    protected PaintBuilder paintBuilder;

    protected float downX;
    protected float downY;

    protected VectorElement previewElement;
    protected VectorElement realElement;

    public InsertVectorElementMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        drawingContext = drawingBoard.getDrawingContext();
        commandManager = drawingBoard.getCommandManager();
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
                previewElement.setupElementByVector(new Vector2(downX, downY, event.getX(), event.getY()));
                return true;
            case MotionEvent.ACTION_UP:
                elementManager.removeElementFromCurrentLayer(previewElement);

                VectorElement element = createRealElement(new Vector2(downX, downY, event.getX(), event.getY()));
                commandManager.executeCommand(new InsertElementCommand(element));
                return true;
            case MotionEvent.ACTION_CANCEL:
                elementManager.removeElementFromCurrentLayer(previewElement);
                return true;
        }
        return false;
    }

    protected void setVectorElement(VectorElement realElement) {
        this.realElement = realElement;
    }

    protected VectorElement createPreviewElement() {
        previewElement = new RectElement();
        previewElement.setPaint(paintBuilder.createPreviewAreaPaint(drawingContext.getPaint()));
        return previewElement;
    }

    protected VectorElement createRealElement(Vector2 vector) {
        VectorElement element = (VectorElement) realElement.clone();
        element.setPaint(new Paint(drawingContext.getPaint()));
        element.setupElementByVector(vector);
        return element;
    }

}
