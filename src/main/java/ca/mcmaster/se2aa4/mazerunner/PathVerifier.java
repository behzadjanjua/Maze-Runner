package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathVerifier {
    private static final Logger logger = LogManager.getLogger();

    public static boolean validatePath(String path, MazeGrid maze, Position entry, Position exit,
            MazeDirection startingDirection) {
        logger.info("Validating path: " + path);
        if (path == null || path.isEmpty()) {
            logger.warn("Invalid path: empty or null.");
            return false;
        }
        // Expand any factorized notation and remove whitespace.
        path = PathFormatter.expandFactorizedPath(path.replaceAll("\\s+", ""));
        logger.info("Expanded path: " + path);
        if (path.isEmpty()) {
            logger.warn("Path expansion resulted in an empty path.");
            return false;
        }
        boolean isValid = validatePathDirection(path, maze, entry, exit, startingDirection);
        if (isValid) {
            logger.info("Path is VALID from entry to exit.");
            return true;
        }
        logger.warn("Path invalid with given entry. Trying flipped configuration.");
        // Flip the starting direction (180° turn) and try again.
        MazeDirection flippedDirection = startingDirection.rotateRight().rotateRight();
        isValid = validatePathDirection(path, maze, exit, entry, flippedDirection);
        if (isValid) {
            logger.info("Path is VALID with flipped entry/exit.");
            return true;
        }
        logger.error("Path is INVALID in both configurations.");
        return false;
    }

    private static boolean validatePathDirection(String path, MazeGrid maze, Position start, Position target,
            MazeDirection startingDirection) {
        // Begin at the starting position and simulate each move.
        Position currentPosition = new Position(start.getRow(), start.getColumn());
        MazeDirection currentDirection = startingDirection;
        MazeExplorer explorer = new MazeExplorer(currentPosition, currentDirection);

        for (int i = 0; i < path.length(); i++) {
            char move = path.charAt(i);
            try {
                explorer.executeCommand(String.valueOf(move));
                currentPosition = explorer.getPosition();
                currentDirection = explorer.getDirection();

                // If the new position is not walkable, the path is invalid.
                if (!maze.isWalkable(currentPosition)) {
                    logger.warn("Hit wall at (" + currentPosition.getColumn() + ", " + currentPosition.getRow() + ")");
                    return false;
                }
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid move: " + move);
                return false;
            }
        }
        // Return true if the final position matches the target.
        return currentPosition.equals(target);
    }
}