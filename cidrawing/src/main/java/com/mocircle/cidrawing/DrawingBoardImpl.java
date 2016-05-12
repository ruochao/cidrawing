package com.mocircle.cidrawing;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.board.ElementManagerImpl;
import com.mocircle.cidrawing.operation.OperationManager;
import com.mocircle.cidrawing.operation.OperationManagerImpl;
import com.mocircle.cidrawing.view.DrawingView;
import com.mocircle.cidrawing.view.DrawingViewProxy;
import com.mocircle.cidrawing.view.DrawingViewProxyImpl;

/**
 * Implementation for {@link DrawingBoard}
 */
public class DrawingBoardImpl implements DrawingBoard {

    private String boardId;
    private DrawingView drawingView;
    private DrawingContext context;
    private ElementManager elementManager;
    private OperationManager operationManager;
    private ConfigManager configManager;
    private DrawingViewProxy viewProxy;

    private PaintBuilder paintBuilder;
    private PaintingBehavior paintingBehavior;

    DrawingBoardImpl(String boardId) {
        this.boardId = boardId;
        context = new DrawingContext(boardId);
        context.addDrawingModeChangedListener(new DrawingContext.DrawingModeChangedListener() {
            @Override
            public void onDrawingModeChanged() {
                if (drawingView != null) {
                    drawingView.notifyViewUpdated();
                }
            }
        });
        elementManager = new ElementManagerImpl(boardId);
        operationManager = new OperationManagerImpl(boardId);
        configManager = new ConfigManagerImpl();
        paintBuilder = new DefaultPaintBuilder();
        paintingBehavior = new DefaultPaintingBehavior(paintBuilder);
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setupDrawingView(DrawingView view) {
        viewProxy = new DrawingViewProxyImpl(view, context, elementManager);
        this.drawingView = view;
        this.drawingView.setViewProxy(viewProxy);
    }

    @Override
    public DrawingView getDrawingView() {
        return drawingView;
    }

    @Override
    public DrawingContext getDrawingContext() {
        return context;
    }

    @Override
    public ElementManager getElementManager() {
        return elementManager;
    }

    public OperationManager getOperationManager() {
        return operationManager;
    }

    @Override
    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public PaintBuilder getPaintBuilder() {
        return paintBuilder;
    }

    @Override
    public void setPaintBuilder(PaintBuilder paintBuilder) {
        this.paintBuilder = paintBuilder;
    }

    @Override
    public PaintingBehavior getPaintingBehavior() {
        return paintingBehavior;
    }

    @Override
    public void setPaintingBehavior(PaintingBehavior paintingBehavior) {
        this.paintingBehavior = paintingBehavior;
    }

}
