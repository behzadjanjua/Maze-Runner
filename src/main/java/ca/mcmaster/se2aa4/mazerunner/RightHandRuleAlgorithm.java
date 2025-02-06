package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RightHandRuleAlgorithm implements MazeSolvingAlgorithm {
    private static final Logger logger = LogManager.getLogger();

    public MazeSolution solve(MazeGrid maze, MazeExplorer explorer) {
        logger.info("Solving maze using the Right-Hand Rule Algorithm");
        while (!explorer.getPosition().equals(maze.getExitPosition())) {
            if (canMove(explorer, maze, DirectionManager.getTurnedRightDirection(explorer.getDirection()))) {
                explorer.turnRight();
                explorer.moveForward();
            } else if (canMove(explorer, maze, explorer.getDirection())) {
                explorer.moveForward();
            } else if (canMove(explorer, maze, DirectionManager.getTurnedLeftDirection(explorer.getDirection()))) {
                explorer.turnLeft();
                explorer.moveForward();
            } else {
                explorer.turnAround();
            }
        }
        logger.info("Maze solved using Right-Hand Rule Algorithm");
        MazeSolution solution = new MazeSolution(explorer.getMovementPath());
        return solution;
    }

    private boolean canMove(MazeExplorer explorer, MazeGrid maze, DirectionManager.Direction direction) {
        Position nextPosition = explorer.getNextPosition(direction);
        return maze.isWalkable(nextPosition);
    }
}