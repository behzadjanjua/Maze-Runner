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

        logger.info("Maze solved using Right-Hand Rule.");
        logger.info("Canonical Path: " + getCanonicalMovementPath());
        logger.info("Factorized Path: " + getFactorizedMovementPath());

    }

    public boolean validatePath(String path) {
        logger.info("Validating path: " + path);

        if (path == null || path.isEmpty()) {
            logger.warn("Invalid path input: Path is empty or null.");
            return false;
        }

        // Convert factorized path to canonical form
        path = expandFactorizedPath(path.replaceAll("\\s+", ""));
        logger.info("Expanded (canonical) path: " + path);

        if (path.isEmpty()) {
            logger.warn("Invalid path after expansion: Path is empty.");
            return false;
        }

        // First pass: Validate using the predefined entry/exit
        boolean isValid = validatePathDirection(path, maze.getEntryColumn(), maze.getEntryRow(), maze.getExitColumn(), maze.getExitRow(), currentDirection);

        if (isValid) {
            logger.info("Path is VALID from predefined Entry to Exit.");
            return true;
        }

        // If first validation fails, swap entry and exit, then retry
        logger.warn("Path is INVALID from predefined Entry to Exit. Attempting flipped entrance/exit...");

        // Reverse the starting direction
        DirectionManager.Direction flippedDirection = DirectionManager.getTurnedRightDirection(
                DirectionManager.getTurnedRightDirection(currentDirection)); // 180-degree turn

        isValid = validatePathDirection(path,
                maze.getExitColumn(), maze.getExitRow(),
                maze.getEntryColumn(), maze.getEntryRow(),
                flippedDirection);

        if (isValid) {
            logger.info("Path is VALID from flipped Entrance to Exit.");
            return true;
        }

        logger.error("Path is INVALID in both predefined and flipped configurations.");
        return false;
    }

    private boolean validatePathDirection(String path, int startColumn, int startRow,
            int targetColumn, int targetRow,
            DirectionManager.Direction startDirection) {
        int tempColumn = startColumn;
        int tempRow = startRow;
        DirectionManager.Direction tempDirection = startDirection;

        for (int i = 0; i < path.length(); i++) {
            char step = path.charAt(i);

            switch (step) {
                case 'F':
                    int[] offsets = DirectionManager.getForwardStepOffsets(tempDirection);
                    tempColumn += offsets[0];
                    tempRow += offsets[1];

                    if (maze.getTile(tempColumn, tempRow) == '#') {
                        logger.warn("Path is invalid: hit a wall at (" + tempColumn + ", " + tempRow + ")");
                        return false;
                    }
                    break;

                case 'L':
                    tempDirection = DirectionManager.getTurnedLeftDirection(tempDirection);
                    break;

                case 'R':
                    tempDirection = DirectionManager.getTurnedRightDirection(tempDirection);
                    break;

                default:
                    logger.warn("Invalid path character: " + step);
                    return false;
            }
        }

        // Check if we reached the target (entry or exit)
        return (tempColumn == targetColumn && tempRow == targetRow);
    }

    private String expandFactorizedPath(String factorizedPath) {
        StringBuilder expandedPath = new StringBuilder();
        int i = 0;

        while (i < factorizedPath.length()) {
            char currentChar = factorizedPath.charAt(i);

            if (Character.isLetter(currentChar)) { // If 'F', 'L', or 'R'
                int repeatCount = 1; // Default is 1 unless a number is before
                int j = i - 1;

                // Check if there's a number priorr to this character
                while (j >= 0 && Character.isDigit(factorizedPath.charAt(j))) {
                    j--;
                }

                if (j < i - 1) { // there IS number exists before this letter
                    repeatCount = Integer.parseInt(factorizedPath.substring(j + 1, i));
                }

                expandedPath.append(String.valueOf(currentChar).repeat(repeatCount));
            } else if (Character.isDigit(currentChar)) {
                // If we find a digit, skip it (since it will be processed in the next letter)
                if (i == 0 || !Character.isLetter(factorizedPath.charAt(i - 1))) {
                    logger.warn("Unexpected number without preceding movement: " + currentChar);
                }
            } else {
                logger.warn("Skipping unexpected character: " + currentChar);
            }
            i++;
        }
        logger.info("Expanded path: " + expandedPath.toString());
        return expandedPath.toString();
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

    public String getCanonicalMovementPath() {
        return String.join("", movementPath);
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