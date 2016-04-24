package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.command.CommandManager;
import com.mocircle.cidrawing.element.DrawElement;

public class ElementOperationMode extends AbstractDrawingMode {

    protected PaintBuilder paintBuilder;
    protected ElementManager elementManager;
    protected CommandManager commandManager;
    
    protected DrawElement element;
    protected Paint originalPaint;
    protected Paint previewPaint;

    public ElementOperationMode() {
    }

    public DrawElement getElement() {
        return element;
    }

    public void setElement(DrawElement element) {
        this.element = element;
        if (element != null) {
            previewPaint = paintBuilder.createPreviewPaint(element.getPaint());
        }
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        paintBuilder = drawingBoard.getPaintBuilder();
        elementManager = drawingBoard.getElementManager();
        commandManager = drawingBoard.getCommandManager();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (element != null) {
                    originalPaint = new Paint(element.getPaint());
                    element.setPaint(previewPaint);
                    return true;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (element != null) {
                    element.setPaint(originalPaint);
                    return true;
                }
        }
        return false;
    }

}
