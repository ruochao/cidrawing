package com.mocircle.cidrawing;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A manager of the drawing board
 */
public class DrawingBoardManager {

    private static DrawingBoardManager instance;

    private Map<String, DrawingBoard> drawingBoardMap = new HashMap<>();

    /**
     * Get the singleton instance of {@link DrawingBoardManager}
     *
     * @return DrawingBoardManager instance
     */
    public static DrawingBoardManager getInstance() {
        if (instance == null) {
            instance = new DrawingBoardManager();
        }
        return instance;
    }

    private DrawingBoardManager() {
    }

    /**
     * Creates a new drawing board
     *
     * @return drawing board just created
     */
    public DrawingBoard createNewBoard() {
        DrawingBoard board = new DrawingBoardImpl(generateBoardId());
        drawingBoardMap.put(board.getBoardId(), board);
        return board;
    }

    /**
     * Looks up the drawing board by unique id
     *
     * @param boardId board id
     * @return drawing board just found
     */
    public DrawingBoard findDrawingBoard(String boardId) {
        return drawingBoardMap.get(boardId);
    }

    private String generateBoardId() {
        return UUID.randomUUID().toString();
    }

}
