package com.mocircle.cidrawing.mode;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.core.Vector2;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.VirtualElement;
import com.mocircle.cidrawing.element.shape.RectElement;
import com.mocircle.cidrawing.element.shape.ShapeElement;
import com.mocircle.cidrawing.utils.DrawUtils;
import com.mocircle.cidrawing.utils.SelectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectionMode extends AbstractDrawingMode {

    private static final String TAG = "SelectionMode";

    private ElementManager elementManager;
    private PaintBuilder paintBuilder;
    private Paint selectionPaint;

    private float downX;
    private float downY;

    private ShapeElement selectionElement;
    private VirtualElement virtualElement;

    public SelectionMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        paintBuilder = drawingBoard.getPaintBuilder();
        selectionPaint = paintBuilder.createSelectionPaint();
    }

    @Override
    public void onLeaveMode() {
        SelectionUtils.clearSelections(elementManager);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                selectionElement = new RectElement();
                selectionElement.setPaint(selectionPaint);
                elementManager.addElementToCurrentLayer(selectionElement);

                return true;
            case MotionEvent.ACTION_MOVE:
                selectionElement.buildShapeFromVector(new Vector2(downX, downY, event.getX(), event.getY()));
                return true;
            case MotionEvent.ACTION_UP:
                elementManager.removeElementFromCurrentLayer(selectionElement);
                SelectionUtils.clearSelections(elementManager);

                if (DrawUtils.isSingleTap(drawingBoard.getDrawingView().getContext(), downX, downY, event.getX(), event.getY())) {

                    // Single selection
                    for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
                        DrawElement element = elementManager.getCurrentElements()[i];
                        if (element.isSelectionEnabled()) {
                            if (element.hitTestForSelection(downX, downY)) {
                                // Only allow one element selected
                                element.setSelected(true);
                                break;
                            }
                        }
                    }

                } else {

                    // Multiple selection
                    List<DrawElement> selectedElements = new ArrayList<>();
                    for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
                        DrawElement element = elementManager.getCurrentElements()[i];
                        element.setSelected(false);
                        if (element.isSelectionEnabled() && element.hitTestForSelection(downX, downY, event.getX(), event.getY())) {
                            selectedElements.add(element);
                        }
                    }
                    if (selectedElements.size() == 1) {
                        // Multiple selection with single element
                        selectedElements.get(0).setSelected(true);
                    } else if (selectedElements.size() > 1) {
                        // Really multiple selection
                        virtualElement = new VirtualElement(selectedElements);
                        virtualElement.setSelected(true);
                        elementManager.addElementToCurrentLayer(virtualElement);
                    }
                }

                return true;
            case MotionEvent.ACTION_CANCEL:
                elementManager.removeElementFromCurrentLayer(selectionElement);
                return true;
        }
        return false;
    }

}
