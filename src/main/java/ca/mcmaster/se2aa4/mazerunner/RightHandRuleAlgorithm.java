package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RightHandRuleAlgorithm implements MazeSolvingAlgorithm {
    private static final Logger logger = LogManager.getLogger();

    public List<String> solve(MazeGrid maze, MazeExplorer explorer) {
        logger.info("Solving maze using Right-Hand Rule");

        // Create a command invoker
        CommandInvoker invoker = new CommandInvoker();

        // Continues until the explorer reaches the exit.
        while (!explorer.getPosition().equals(maze.getExitPosition())) {
            // Checks if turning right is possible.
            if (canMove(explorer, maze, explorer.getDirection().rotateRight())) {
                invoker.addCommand(new TurnRightCommand(explorer));
                invoker.executeCommand();

                invoker.addCommand(new MoveForwardCommand(explorer));
                invoker.executeCommand();
            } else if (canMove(explorer, maze, explorer.getDirection())) {
                // Otherwise, the explorer moves straight.
                invoker.addCommand(new MoveForwardCommand(explorer));
                invoker.executeCommand();
            } else if (canMove(explorer, maze, explorer.getDirection().rotateLeft())) {
                invoker.addCommand(new TurnLeftCommand(explorer));
                invoker.executeCommand();

                invoker.addCommand(new MoveForwardCommand(explorer));
                invoker.executeCommand();
            } else {
                // If no option is available the explorer turns around.
                invoker.addCommand(new TurnAroundCommand(explorer));
                invoker.executeCommand();
            }
        }
        logger.info("Maze solved with Right-Hand Rule");
        return invoker.getMovementPath();
    }

    private boolean canMove(MazeExplorer explorer, MazeGrid maze, MazeDirection direction) {
        // Gets the next position for the given direction and check if it's walkable.
        Position nextPosition = explorer.getNextPosition(direction);
        return maze.isWalkable(nextPosition);
    }
}