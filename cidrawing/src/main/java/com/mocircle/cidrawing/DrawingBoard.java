package com.mocircle.cidrawing;

import com.mocircle.cidrawing.board.ElementManager;
import com.mocircle.cidrawing.command.CommandManager;
import com.mocircle.cidrawing.view.DrawingView;

public interface DrawingBoard {

    String getBoardId();

    void setupDrawingView(DrawingView view);

    DrawingView getDrawingView();

    DrawingContext getDrawingContext();

    ElementManager getElementManager();

    CommandManager getCommandManager();

    ConfigManager getConfigManager();

    PaintBuilder getPaintBuilder();

    void setPaintBuilder(PaintBuilder paintBuilder);

    PaintingBehavior getPaintingBehavior();

    void setPaintingBehavior(PaintingBehavior paintingBehavior);

}
