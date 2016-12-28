package com.mocircle.cidrawing.mode.selection;

import android.graphics.Path;
import android.view.MotionEvent;

import com.mocircle.cidrawing.PaintBuilder;
import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.core.CiPaint;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.VirtualElement;
import com.mocircle.cidrawing.mode.AbstractDrawingMode;
import com.mocircle.cidrawing.utils.DrawUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectionMode extends AbstractDrawingMode {

    private static final String TAG = "SelectionMode";

    protected ElementManager elementManager;
    protected PaintBuilder paintBuilder;
    protected CiPaint selectionPaint;

    protected float downX;
    protected float downY;

    protected DrawElement selectionElement;
    protected VirtualElement virtualElement;

    public SelectionMode() {
    }

    @Override
    public void setDrawingBoardId(String boardId) {
        super.setDrawingBoardId(boardId);
        elementManager = drawingBoard.getElementManager();
        paintBuilder = drawingBoard.getPaintBuilder();
        selectionPaint = paintBuilder.createRectSelectionToolPaint();
    }

    @Override
    public void onLeaveMode() {
        elementManager.clearSelection();
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                selectionElement = createSelectionElement();
                selectionElement.setPaint(selectionPaint);
                elementManager.addAdornmentToCurrentLayer(selectionElement);
                return true;
            case MotionEvent.ACTION_UP:
                elementManager.removeAdornmentFromCurrentLayer(selectionElement);
                elementManager.clearSelection();

                if (DrawUtils.isSingleTap(drawingBoard.getDrawingView().getContext(), downX, downY, event)) {

                    // Single selection
                    for (int i = elementManager.getCurrentObjects().length - 1; i >= 0; i--) {
                        DrawElement element = elementManager.getCurrentObjects()[i];
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
                    for (int i = elementManager.getCurrentObjects().length - 1; i >= 0; i--) {
                        DrawElement element = elementManager.getCurrentObjects()[i];
                        element.setSelected(false);
                        if (element.isSelectionEnabled() && element.hitTestForSelection(getSelectionPath())) {
                            selectedElements.add(element);
                        }
                    }
                    if (selectedElements.size() == 1) {
                        // Multiple selection with single element
                        selectedElements.get(0).setSelected(true);
                    } else if (selectedElements.size() > 1) {
                        // Real multiple selection
                        virtualElement = new VirtualElement(selectedElements);
                        virtualElement.setSelected(true);
                        elementManager.addAdornmentToCurrentLayer(virtualElement);
                    }
                }

                return true;
            case MotionEvent.ACTION_CANCEL:
                elementManager.removeAdornmentFromCurrentLayer(selectionElement);
                return true;
        }
        return false;
    }

    protected abstract DrawElement createSelectionElement();

    protected abstract Path getSelectionPath();

}
