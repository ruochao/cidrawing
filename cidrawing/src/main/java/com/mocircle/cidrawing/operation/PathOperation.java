package com.mocircle.cidrawing.operation;

import android.graphics.Path;

import com.mocircle.cidrawing.element.BasePathElement;
import com.mocircle.cidrawing.element.DrawElement;
import com.mocircle.cidrawing.element.PathElement;

import java.util.List;

public class PathOperation extends SelectedElementsOperation {

    private PathElement pathElement;
    private Path.Op pathOp = Path.Op.UNION;

    public PathOperation() {
    }

    public PathOperation(List<DrawElement> elements) {
        this.elements = elements;
    }

    public Path.Op getPathOp() {
        return pathOp;
    }

    public void setPathOp(Path.Op pathOp) {
        this.pathOp = pathOp;
    }

    @Override
    public boolean isExecutable() {
        super.isExecutable();
        int pathElementCount = 0;
        for (DrawElement element : elements) {
            if (element instanceof BasePathElement) {
                pathElementCount++;
            }
        }
        return pathElementCount >= 2;
    }

    @Override
    public boolean doOperation() {
        Path path = null;
        boolean firstElement = true;
        for (int i = 0; i < elements.size(); i++) {
            DrawElement element = elements.get(i);
            if (element instanceof BasePathElement) {
                if (firstElement) {
                    path = ((BasePathElement) element).getActualElementPath();
                    firstElement = false;
                } else {
                    BasePathElement shape = (BasePathElement) element;
                    path.op(shape.getActualElementPath(), pathOp);
                }
            }
        }

        // Create path element to hold the merged paths
        pathElement = new PathElement();
        pathElement.setElementPath(path);
        pathElement.setPaint(drawingBoard.getDrawingContext().getPaint());

        // Add path element
        for (DrawElement element : elements) {
            if (element instanceof BasePathElement) {
                elementManager.removeElementFromCurrentLayer(element);
            }
        }
        elementManager.addElementToCurrentLayer(pathElement);

        // Re-select elements
        elementManager.clearSelection();
        elementManager.selectElement(pathElement);
        drawingBoard.getDrawingView().notifyViewUpdated();

        return true;
    }

    @Override
    public void undo() {
        // Add path element
        for (DrawElement element : elements) {
            if (element instanceof BasePathElement) {
                elementManager.addElementToCurrentLayer(element);
            }
        }
        elementManager.removeElementFromCurrentLayer(pathElement);

        // Re-select elements
        elementManager.clearSelection();
        elementManager.selectElements(elements);
        drawingBoard.getDrawingView().notifyViewUpdated();
    }
}
