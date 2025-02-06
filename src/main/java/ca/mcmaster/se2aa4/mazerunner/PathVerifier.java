package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathVerifier {
    private static final Logger logger = LogManager.getLogger();

    public static boolean validatePath(String path, MazeGrid maze, Position entry, Position exit, DirectionManager.Direction startingDirection) {
        logger.info("Validating path: " + path);
        if (path == null || path.isEmpty()) {
            logger.warn("Invalid path input: Path is empty or null.");
            return false;
        }
        path = PathFormatter.expandFactorizedPath(path.replaceAll("\\s+", ""));
        logger.info("Expanded (canonical) path: " + path);
        if (path.isEmpty()) {
            logger.warn("Invalid path after expansion: Path is empty.");
            return false;
        }
        boolean isValid = validatePathDirection(path, maze, entry, exit, startingDirection);
        if (isValid) {
            logger.info("Path is VALID from predefined Entry to Exit.");
            return true;
        }
        logger.warn("Path is INVALID from predefined Entry to Exit. Attempting flipped entrance/exit...");
        DirectionManager.Direction flippedDirection = DirectionManager.getTurnedRightDirection(
            DirectionManager.getTurnedRightDirection(startingDirection));
        isValid = validatePathDirection(path, maze, exit, entry, flippedDirection);
        if (isValid) {
            logger.info("Path is VALID from flipped Entrance to Exit.");
            return true;
        }
        logger.error("Path is INVALID in both predefined and flipped configurations.");
        return false;
    }

    private static boolean validatePathDirection(String path, MazeGrid maze, Position start, Position target, DirectionManager.Direction startingDirection) {
        Position currentPosition = new Position(start.getRow(), start.getColumn());
        DirectionManager.Direction currentDirection = startingDirection;
        for (int i = 0; i < path.length(); i++) {
            char move = path.charAt(i);
            switch (move) {
                case 'F':
                    int[] offsets = DirectionManager.getForwardStepOffsets(currentDirection);
                    currentPosition = new Position(currentPosition.getRow() + offsets[1], currentPosition.getColumn() + offsets[0]);
                    if (!maze.isWalkable(currentPosition)) {
                        logger.warn("Path is invalid: hit a wall at (" + currentPosition.getColumn() + ", " + currentPosition.getRow() + ")");
                        return false;
                    }
                    break;
                case 'L':
                    currentDirection = DirectionManager.getTurnedLeftDirection(currentDirection);
                    break;
                case 'R':
                    currentDirection = DirectionManager.getTurnedRightDirection(currentDirection);
                    break;
                default:
                    logger.warn("Invalid path character: " + move);
                    return false;
            }
        }
        return currentPosition.equals(target);
    }
}