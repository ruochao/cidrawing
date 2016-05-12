package com.mocircle.cidrawing.operation;

import java.util.List;

public interface OperationManager {

    void executeOperation(DrawingOperation operation);

    List<DrawingOperation> getOperationHistory();

    void undo();

    void undoToOperation(DrawingOperation operation);

    void redo();

    void redoToOperation(DrawingOperation operation);

}
