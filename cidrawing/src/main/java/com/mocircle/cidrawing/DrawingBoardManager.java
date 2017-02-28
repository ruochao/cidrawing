package com.mocircle.cidrawing;

import android.text.TextUtils;

import org.json.JSONObject;

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
    public DrawingBoard createDrawingBoard() {
        DrawingBoard board = new DrawingBoardImpl(generateBoardId());
        drawingBoardMap.put(board.getBoardId(), board);
        return board;
    }

    /**
     * Pre-create a new drawing board from json data (boardId)
     *
     * @param object json data
     * @return drawing board
     */
    public DrawingBoard createDrawingBoard(JSONObject object) {
        String boardId = extractBoardId(object);
        if (!TextUtils.isEmpty(boardId)) {
            DrawingBoard board = new DrawingBoardImpl(boardId);
            drawingBoardMap.put(board.getBoardId(), board);
            return board;
        } else {
            return null;
        }
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

    private String extractBoardId(JSONObject object) {
        return object.optString(DrawingBoardImpl.KEY_BOARD_ID);
    }

}
