package com.mocircle.cidrawing.command;

import java.util.List;

public interface CommandManager {

    void executeCommand(DrawingCommand command);

    List<DrawingCommand> getCommandHistory();

    void undo();

    void undoToCommand(DrawingCommand command);

    void redo();

    void redoToCommand(DrawingCommand command);

}
