package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class MazeExplorer {
    private Position currentPosition;
    private MazeDirection currentDirection;
    private List<String> movementPath; // Stores movement history as a sequence of "F", "L", "R"

    public MazeExplorer(Position startingPosition, MazeDirection startingDirection) {
        this.currentPosition = startingPosition;
        this.currentDirection = startingDirection;
        this.movementPath = new ArrayList<>();
    }

    public Position getPosition() {
        return currentPosition;
    }

    public MazeDirection getDirection() {
        return currentDirection;
    }

    public List<String> getMovementPath() {
        return movementPath;
    }

    public Position getNextPosition(MazeDirection direction) {
        int[] offset = direction.getOffset();
        // Calculate next position based on direction offsets.
        int nextRow = currentPosition.getRow() + offset[1]; // Vertical movement
        int nextColumn = currentPosition.getColumn() + offset[0]; // Horizontal movement
        return new Position(nextRow, nextColumn);
    }

    // These methods are now used by the command pattern
    public void moveForward() {
        // Move forward in the current direction.
        currentPosition = getNextPosition(currentDirection);
        movementPath.add("F"); // Log forward movement
    }

    public void turnLeft() {
        currentDirection = currentDirection.rotateLeft();
        movementPath.add("L");
    }

    public void turnRight() {
        currentDirection = currentDirection.rotateRight();
        movementPath.add("R");
    }

    public void turnAround() {
        // Turn 180° by rotating right twice.
        currentDirection = currentDirection.rotateRight().rotateRight();
        movementPath.add("R");
        movementPath.add("R");
    }
}