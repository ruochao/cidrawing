package com.mocircle.cidrawing.mode;

import android.view.MotionEvent;

import com.mocircle.android.logging.CircleLog;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.element.DrawElement;

public class PointerMode extends AbstractDrawingMode {

    private static final String TAG = "PointerMode";

    private ElementManager elementManager;
    private DrawingMode currentMode;

    private SelectionMode selectionMode = new SelectionMode();
    private ReferencePointMode referencePointMode = new ReferencePointMode();
    private MoveMode moveMode = new MoveMode();
    private RotateMode rotateMode = new RotateMode();
    private ResizeMode resizeMode = new ResizeMode();

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);

        elementManager = drawingBoard.getElementManager();

        selectionMode.setDrawingBoardId(boardId);
        referencePointMode.setDrawingBoardId(boardId);
        moveMode.setDrawingBoardId(boardId);
        rotateMode.setDrawingBoardId(boardId);
        resizeMode.setDrawingBoardId(boardId);
    }

    @Override
    public void onLeaveMode() {
        if (currentMode != null) {
            currentMode.onLeaveMode();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Auto switch mode
                hitTestForSwitchingMode(event.getX(), event.getY());

                currentMode.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                currentMode.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                currentMode.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                currentMode.onTouchEvent(event);
                break;
        }
        return true;
    }


    private void hitTestForSwitchingMode(float x, float y) {
        for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
            DrawElement element = elementManager.getCurrentElements()[i];
            if (element.isSelected()) {

                if (element.hitTestForReferencePoint(x, y)) {
                    referencePointMode.setElement(element);
                    currentMode = referencePointMode;
                    CircleLog.i(TAG, "Switch to ReferencePointMode");
                    return;
                }

                if (element.hitTestForRotationHandle(x, y)) {
                    rotateMode.setElement(element);
                    currentMode = rotateMode;
                    CircleLog.i(TAG, "Switch to RotateMode");
                    return;
                }

                ResizingDirection direction = element.hitTestForResizingHandle(x, y);
                if (direction != ResizingDirection.NONE) {
                    resizeMode.setElement(element);
                    resizeMode.setResizingDirection(direction);
                    currentMode = resizeMode;
                    CircleLog.i(TAG, "Switch to ResizeMode");
                    return;
                }

                if (element.hitTestForSelection(x, y)) {
                    moveMode.setElement(element);
                    currentMode = moveMode;
                    CircleLog.i(TAG, "Switch to MoveMode");
                    return;
                }
            }
        }
        currentMode = selectionMode;
        CircleLog.i(TAG, "Switch to SelectionMode");
    }

}
