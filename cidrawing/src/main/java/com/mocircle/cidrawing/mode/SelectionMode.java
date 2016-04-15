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

import java.util.ArrayList;
import java.util.List;

public class SelectionMode extends AbstractDrawingMode {

    private static final String TAG = "SelectionMode";

    private PaintBuilder paintBuilder;

    private ElementManager elementManager;

    private float downX;
    private float downY;

    private ShapeElement shapeElement;
    private VirtualElement virtualElement;

    public SelectionMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        paintBuilder = drawingBoard.getPaintBuilder();
    }

    @Override
    public void onLeaveMode() {
        DrawUtils.clearSelections(elementManager);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                shapeElement = new RectElement();
                Paint p = paintBuilder.createSelectionPaint();
                shapeElement.setPaint(p);
                elementManager.addElementToCurrentLayer(shapeElement);

                return true;
            case MotionEvent.ACTION_MOVE:
                shapeElement.buildShapeFromVector(new Vector2(downX, downY, event.getX(), event.getY()));
                return true;
            case MotionEvent.ACTION_UP:
                elementManager.removeElementFromCurrentLayer(shapeElement);
                if (virtualElement != null) {
                    elementManager.removeElementFromCurrentLayer(virtualElement);
                }

                if (DrawUtils.isSingleTap(drawingBoard.getDrawingView().getContext(), downX, downY, event.getX(), event.getY())) {

                    // Single selection
                    boolean hitTestResult = true;
                    for (int i = elementManager.getCurrentElements().length - 1; i >= 0; i--) {
                        DrawElement element = elementManager.getCurrentElements()[i];
                        if (element.isSelectionEnabled()) {
                            if (element.hitTestForSelection(downX, downY)) {
                                // Only allow one element selected
                                element.setSelected(hitTestResult);
                                hitTestResult = false;
                            } else {
                                element.setSelected(false);
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
                elementManager.removeElementFromCurrentLayer(shapeElement);
                return true;
        }
        return false;
    }

}
