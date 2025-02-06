package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class MazeExplorer {
    private Position currentPosition;
    private DirectionManager.Direction currentDirection;
    private List<String> movementPath;

    public MazeExplorer(Position startingPosition, DirectionManager.Direction startingDirection) {
        this.currentPosition = startingPosition;
        this.currentDirection = startingDirection;
        this.movementPath = new ArrayList<>();
    }

    public Position getPosition() {
        return currentPosition;
    }

    public DirectionManager.Direction getDirection() {
        return currentDirection;
    }

    public List<String> getMovementPath() {
        return movementPath;
    }

    public Position getNextPosition(DirectionManager.Direction direction) {
        int[] offsets = DirectionManager.getForwardStepOffsets(direction);
        int nextRow = currentPosition.getRow() + offsets[1];
        int nextColumn = currentPosition.getColumn() + offsets[0];
        return new Position(nextRow, nextColumn);
    }

    public void moveForward() {
        currentPosition = getNextPosition(currentDirection);
        movementPath.add("F");
    }

    public void turnLeft() {
        currentDirection = DirectionManager.getTurnedLeftDirection(currentDirection);
        movementPath.add("L");
    }

    public void turnRight() {
        currentDirection = DirectionManager.getTurnedRightDirection(currentDirection);
        movementPath.add("R");
    }

    public void turnAround() {
        currentDirection = DirectionManager.getTurnedRightDirection(
            DirectionManager.getTurnedRightDirection(currentDirection));
        movementPath.add("R");
        movementPath.add("R");
    }
}