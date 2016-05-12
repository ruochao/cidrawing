package com.mocircle.cidrawing.operation;

import com.mocircle.android.logging.CircleLog;

import java.util.List;
import java.util.Stack;

public class OperationManagerImpl implements OperationManager {

    private static final String TAG = "OperationManagerImpl";

    private Stack<DrawingOperation> undoOperationList = new Stack<>();
    private Stack<DrawingOperation> redoOperationList = new Stack<>();

    private String boardId;

    public OperationManagerImpl(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public void executeOperation(DrawingOperation operation) {
        CircleLog.i(TAG, "Execute operation: " + operation.getClass().getSimpleName());
        operation.setDrawingBoardId(boardId);
        if (operation.isExecutable()) {
            boolean result = operation.doOperation();
            if (result) {
                undoOperationList.push(operation);
                redoOperationList.clear();
            }
            CircleLog.i(TAG, "Execute operation result: " + result);
        } else {
            CircleLog.i(TAG, "Operation is not executable now");
        }
    }

    @Override
    public List<DrawingOperation> getOperationHistory() {
        return undoOperationList;
    }

    @Override
    public void undo() {
        if (!undoOperationList.isEmpty()) {
            DrawingOperation operation = undoOperationList.pop();
            if (operation != null) {
                operation.undo();
                redoOperationList.push(operation);
            }
        }
    }

    @Override
    public void undoToOperation(DrawingOperation operation) {
        for (int i = undoOperationList.size() - 1; i >= 0; i--) {
            if (undoOperationList.get(i) == operation) {
                int index = undoOperationList.size() - i;
                for (int j = 0; j < index; j++) {
                    undo();
                }
                break;
            }
        }
    }

    @Override
    public void redo() {
        if (!redoOperationList.isEmpty()) {
            DrawingOperation operation = redoOperationList.pop();
            if (operation != null) {
                operation.redo();
                undoOperationList.push(operation);
            }
        }
    }

    @Override
    public void redoToOperation(DrawingOperation operation) {
        for (int i = redoOperationList.size() - 1; i >= 0; i--) {
            if (redoOperationList.get(i) == operation) {
                int index = redoOperationList.size() - i;
                for (int j = 0; j < index; j++) {
                    redo();
                }
                break;
            }
        }
    }

}
