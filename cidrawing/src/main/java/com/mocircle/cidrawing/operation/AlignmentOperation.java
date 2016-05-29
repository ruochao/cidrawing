package com.mocircle.cidrawing.operation;

import android.graphics.RectF;

import com.mocircle.cidrawing.element.DrawElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignmentOperation extends SelectedElementsOperation {

    public enum AlignmentType {
        // Horizontal
        HorizontalLeft,
        HorizontalCenter,
        HorizontalRight,

        // Vertical
        VerticalTop,
        VerticalMiddle,
        VerticalBottom,
    }

    private AlignmentType alignmentType = AlignmentType.HorizontalLeft;
    private Map<DrawElement, Float> offsetMap = new HashMap<>();

    public AlignmentOperation() {
    }

    public AlignmentOperation(List<DrawElement> elements) {
        this.elements = elements;
    }

    public AlignmentType getAlignmentType() {
        return alignmentType;
    }

    public void setAlignmentType(AlignmentType alignmentType) {
        this.alignmentType = alignmentType;
    }

    @Override
    public boolean doOperation() {
        RectF box = new RectF();
        for (DrawElement element : elements) {
            box.union(element.getOuterBoundingBox());
        }
        switch (alignmentType) {
            case HorizontalLeft:
                alignLeft(box);
                break;
            case HorizontalCenter:
                alignCenter(box);
                break;
            case HorizontalRight:
                alignRight(box);
                break;
            case VerticalTop:
                alignTop(box);
                break;
            case VerticalMiddle:
                alignMiddle(box);
                break;
            case VerticalBottom:
                alignBottom(box);
                break;
        }
        drawingBoard.getDrawingView().notifyViewUpdated();
        return true;
    }

    @Override
    public void undo() {
        switch (alignmentType) {
            case HorizontalLeft:
            case HorizontalCenter:
            case HorizontalRight:
                for (DrawElement element : elements) {
                    float offset = offsetMap.get(element);
                    element.move(-offset, 0);
                }
                break;
            case VerticalTop:
            case VerticalMiddle:
            case VerticalBottom:
                for (DrawElement element : elements) {
                    float offset = offsetMap.get(element);
                    element.move(0, -offset);
                }
                break;
        }
        drawingBoard.getDrawingView().notifyViewUpdated();
    }

    private void alignLeft(RectF box) {
        for (DrawElement element : elements) {
            float offset = box.left - element.getOuterBoundingBox().left;
            offsetMap.put(element, offset);
            element.move(offset, 0);
        }
    }

    private void alignCenter(RectF box) {
        for (DrawElement element : elements) {
            float offset = box.centerX() - element.getOuterBoundingBox().centerX();
            offsetMap.put(element, offset);
            element.move(offset, 0);
        }
    }

    private void alignRight(RectF box) {
        for (DrawElement element : elements) {
            float offset = box.right - element.getOuterBoundingBox().right;
            offsetMap.put(element, offset);
            element.move(offset, 0);
        }
    }

    private void alignTop(RectF box) {
        for (DrawElement element : elements) {
            float offset = box.top - element.getOuterBoundingBox().top;
            offsetMap.put(element, offset);
            element.move(0, offset);
        }
    }

    private void alignMiddle(RectF box) {
        for (DrawElement element : elements) {
            float offset = box.centerY() - element.getOuterBoundingBox().centerY();
            offsetMap.put(element, offset);
            element.move(0, offset);
        }
    }

    private void alignBottom(RectF box) {
        for (DrawElement element : elements) {
            float offset = box.bottom - element.getOuterBoundingBox().bottom;
            offsetMap.put(element, offset);
            element.move(0, offset);
        }
    }

}
