package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class Explorer {
    private int currentColumn, currentRow; // Explorer's current position in the maze
    private DirectionManager.Direction currentDirection; // Direction explorer is facing
    private Maze maze; // The maze the explorer is navigating
    private List<String> movementPath; // Keeps track of all movements (F, L, R)

    public Explorer(Maze maze) {
        this.maze = maze;
        this.currentColumn = maze.getEntryColumn();
        this.currentRow = maze.getEntryRow();
        this.currentDirection = DirectionManager.Direction.EAST; // Start facing east
        this.movementPath = new ArrayList<>();
    }

    // Move one step forward if the next tile is a passage
    public void moveForwardIfPossible() {
        int[] forwardStepOffsets = DirectionManager.getForwardStepOffsets(currentDirection);
        int nextColumn = currentColumn + forwardStepOffsets[0];
        int nextRow = currentRow + forwardStepOffsets[1];

        // Check if the tile ahead is passable
        if (maze.getTile(nextColumn, nextRow) == ' ') {
            currentColumn = nextColumn;
            currentRow = nextRow;
            movementPath.add("F"); // Record the forward movement
        }
    }

    // Turn left and record the movement
    public void turnToLeft() {
        currentDirection = DirectionManager.getTurnedLeftDirection(currentDirection);
        movementPath.add("L");
    }

    // Turn right and record the movement
    public void turnToRight() {
        currentDirection = DirectionManager.getTurnedRightDirection(currentDirection);
        movementPath.add("R");
    }

    // Return the path as a list of movements (canonical form)
    public List<String> getCanonicalMovementPath() {
        return movementPath;
    }

    // Compress repeated movements into a factorized form (e.g., "FFF" -> "3F")
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