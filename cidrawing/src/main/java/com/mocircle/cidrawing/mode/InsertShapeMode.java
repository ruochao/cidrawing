package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.DrawingContext;
import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.command.CommandManager;
import com.mocircle.cidrawing.command.InsertShapeCommand;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.shape.ShapeElement;

public class InsertShapeMode extends AbstractDrawingMode {

    private static final String TAG = "InsertShapeMode";

    private ElementManager elementManager;
    private DrawingContext drawingContext;
    private CommandManager commandManager;
    private PaintBuilder paintBuilder;

    private float downX;
    private float downY;

    private ShapeElement previewElement;
    private Class<? extends ShapeElement> shapeType;

    public InsertShapeMode() {
    }

    public void setShapeType(Class<? extends ShapeElement> shapeType) {
        this.shapeType = shapeType;
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

                previewElement = createPreviewShape();
                elementManager.addElementToCurrentLayer(previewElement);
                return true;
            case MotionEvent.ACTION_MOVE:
                previewElement.buildShapeFromVector(new Vector2(downX, downY, event.getX(), event.getY()));
                return true;
            case MotionEvent.ACTION_UP:
                elementManager.removeElementFromCurrentLayer(previewElement);

                ShapeElement shapeElement = createShape(new Vector2(downX, downY, event.getX(), event.getY()));
                commandManager.executeCommand(new InsertShapeCommand(shapeElement));
                return true;
            case MotionEvent.ACTION_CANCEL:
                elementManager.removeElementFromCurrentLayer(previewElement);
                return true;
        }
        return false;
    }

    private ShapeElement createPreviewShape() {
        previewElement = getShapeInstance();
        previewElement.setPaint(paintBuilder.createPreviewPaint(drawingContext.getPaint()));
        return previewElement;
    }

    private ShapeElement createShape(Vector2 vector) {
        ShapeElement element = getShapeInstance();
        element.buildShapeFromVector(vector);
        element.setPaint(new Paint(drawingContext.getPaint()));
        return element;
    }

    private ShapeElement getShapeInstance() {
        try {
            return shapeType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

}
