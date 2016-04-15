package com.mocircle.cidrawing;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DrawingBoardManager {

    private static DrawingBoardManager instance;

    private Map<String, DrawingBoard> drawingBoardMap = new HashMap<>();

    public static DrawingBoardManager getInstance() {
        if (instance == null) {
            instance = new DrawingBoardManager();
        }
        return instance;
    }

    private DrawingBoardManager() {
    }

    public DrawingBoard createNewBoard() {
        DrawingBoard board = new DrawingBoardImpl(generateBoardId());
        drawingBoardMap.put(board.getBoardId(), board);
        return board;
    }

    public DrawingBoard findDrawingBoard(String boardId) {
        return drawingBoardMap.get(boardId);
    }

    private String generateBoardId() {
        return UUID.randomUUID().toString();
    }

}
