package ca.mcmaster.se2aa4.mazerunner.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
    private final List<MovementCommand> commandQueue = new ArrayList<>();
    private final List<String> executedMovements = new ArrayList<>();

    public void addCommand(MovementCommand command) {
        commandQueue.add(command);
    }

    public String executeCommand() {
        if (!commandQueue.isEmpty()) {
            MovementCommand command = commandQueue.remove(0);
            String result = command.execute();
            executedMovements.add(result);
            return result;
        }
        return null;
    }

    public List<String> executeAllCommands() {
        while (!commandQueue.isEmpty()) {
            executeCommand();
        }
        return getMovementPath();
    }

    public List<String> getMovementPath() {
        return executedMovements;
    }

    public void clearQueue() {
        commandQueue.clear();
    }
}