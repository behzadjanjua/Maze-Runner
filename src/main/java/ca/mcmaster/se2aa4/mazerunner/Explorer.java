package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Explorer {
    private static final Logger logger = LogManager.getLogger();

    private int currentColumn, currentRow;
    private DirectionManager.Direction currentDirection;
    private Maze maze;
    private List<String> movementPath;

    public Explorer(Maze maze) {
        this.maze = maze;
        this.currentColumn = maze.getEntryColumn();
        this.currentRow = maze.getEntryRow();
        this.currentDirection = DirectionManager.Direction.EAST; // Start facing east
        this.movementPath = new ArrayList<>();
    }

    public void solveRightHandMaze() {
        logger.info("Solving maze using the Right-Hand Rule...");

        while (currentColumn != maze.getExitColumn() || currentRow != maze.getExitRow()) {
            if (canMoveRight()) {
                turnToRight();
                moveForward();
            } else if (canMoveForward()) {
                moveForward();
            } else if (canMoveLeft()) {
                turnToLeft();
                moveForward();
            } else {
                turnAround();
            }
        }

        logger.info("Maze solved using Right-Hand Rule. Path: " + getFactorizedMovementPath());
    }

    public boolean validatePath(String path) {
        logger.info("Validating path: " + path);
    
        int tempColumn = currentColumn;
        int tempRow = currentRow;
        DirectionManager.Direction tempDirection = currentDirection;
    
        int i = 0;
        while (i < path.length()) {
            // Extract the number before a command
            int repeat = 0;
            while (i < path.length() && Character.isDigit(path.charAt(i))) {
                repeat = repeat * 10 + (path.charAt(i) - '0');
                i++;
            }
    
            // Default to 1 if no number was specified
            if (repeat == 0) {
                repeat = 1;
            }
    
            // Ensure we still have a valid movement command
            if (i >= path.length()) {
                logger.warn("Path validation error: Expected movement command after number.");
                return false;
            }
    
            char step = path.charAt(i);
    
            // Execute the movement `repeat` times
            for (int r = 0; r < repeat; r++) {
                switch (step) {
                    case 'F': // Move forward
                        int[] offsets = DirectionManager.getForwardStepOffsets(tempDirection);
                        tempColumn += offsets[0];
                        tempRow += offsets[1];
    
                        // Check if moving forward hits a wall
                        if (maze.getTile(tempColumn, tempRow) == '#') {
                            logger.warn("Path is invalid: hit a wall at (" + tempColumn + ", " + tempRow + ")");
                            return false;
                        }
                        break;
    
                    case 'L': // Turn left
                        tempDirection = DirectionManager.getTurnedLeftDirection(tempDirection);
                        break;
    
                    case 'R': // Turn right
                        tempDirection = DirectionManager.getTurnedRightDirection(tempDirection);
                        break;
    
                    default:
                        logger.warn("Invalid path character: " + step);
                        return false;
                }
            }
            i++; // Move to the next character
        }
    
        // Check if the final position matches the exit point
        boolean isValid = (tempColumn == maze.getExitColumn() && tempRow == maze.getExitRow());
        logger.info("Path validation completed. Result: " + (isValid ? "Valid" : "Invalid"));
        return isValid;
    }

    private boolean canMoveForward() {
        return canMove(DirectionManager.getForwardStepOffsets(currentDirection));
    }

    private boolean canMoveRight() {
        DirectionManager.Direction rightDir = DirectionManager.getTurnedRightDirection(currentDirection);
        return canMove(DirectionManager.getForwardStepOffsets(rightDir));
    }

    private boolean canMoveLeft() {
        DirectionManager.Direction leftDir = DirectionManager.getTurnedLeftDirection(currentDirection);
        return canMove(DirectionManager.getForwardStepOffsets(leftDir));
    }

    private boolean canMove(int[] offsets) {
        int nextColumn = currentColumn + offsets[0];
        int nextRow = currentRow + offsets[1];
        return maze.getTile(nextColumn, nextRow) == ' ';
    }

    private void moveForward() {
        int[] offsets = DirectionManager.getForwardStepOffsets(currentDirection);
        currentColumn += offsets[0];
        currentRow += offsets[1];
        movementPath.add("F");
    }

    private void turnToLeft() {
        currentDirection = DirectionManager.getTurnedLeftDirection(currentDirection);
        movementPath.add("L");
    }

    private void turnToRight() {
        currentDirection = DirectionManager.getTurnedRightDirection(currentDirection);
        movementPath.add("R");
    }

    private void turnAround() {
        currentDirection = DirectionManager.getTurnedRightDirection(
            DirectionManager.getTurnedRightDirection(currentDirection));
        movementPath.add("R");
        movementPath.add("R");
    }

    public List<String> getCanonicalMovementPath() {
        return movementPath;
    }

    public String getFactorizedMovementPath() {
        StringBuilder factorizedPath = new StringBuilder();
        int repeatCount = 1;

        for (int i = 1; i < movementPath.size(); i++) {
            if (movementPath.get(i).equals(movementPath.get(i - 1))) {
                repeatCount++;
            } else {
                if (repeatCount > 1) {
                    factorizedPath.append(repeatCount);
                }
                factorizedPath.append(movementPath.get(i - 1));
                repeatCount = 1;
            }
        }

        if (repeatCount > 1) {
            factorizedPath.append(repeatCount);
        }
        factorizedPath.append(movementPath.get(movementPath.size() - 1));
        return factorizedPath.toString();
    }

}