package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RightHandRuleAlgorithm implements MazeSolvingAlgorithm {
    private static final Logger logger = LogManager.getLogger();
    
    public java.util.List<String> solve(MazeGrid maze, MazeExplorer explorer) {
        logger.info("Solving maze using Right-Hand Rule");
        // Continues until the explorer reaches the exit.
        while (!explorer.getPosition().equals(maze.getExitPosition())) {
            // Checks if turning right is possible.
            if (canMove(explorer, maze, explorer.getDirection().rotateRight())) {
                explorer.turnRight();
                explorer.moveForward();
            } else if (canMove(explorer, maze, explorer.getDirection())) {
                // Otherwise, the explorer moves straight.
                explorer.moveForward();
            } else if (canMove(explorer, maze, explorer.getDirection().rotateLeft())) {
                explorer.turnLeft();
                explorer.moveForward();
            } else {
                // If no option is availabl the explorer turn around.
                explorer.turnAround();
            }
        }
        logger.info("Maze solved with Right-Hand Rule");
        return explorer.getMovementPath();
    }
    
    private boolean canMove(MazeExplorer explorer, MazeGrid maze, MazeDirection direction) {
        // Gets the next position for the given direction and check if it's walkable.
        Position nextPosition = explorer.getNextPosition(direction);
        return maze.isWalkable(nextPosition);
    }
}