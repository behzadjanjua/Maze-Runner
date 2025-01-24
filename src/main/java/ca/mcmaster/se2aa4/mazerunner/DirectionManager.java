package ca.mcmaster.se2aa4.mazerunner;

public class DirectionManager {
    public enum Direction { NORTH, EAST, SOUTH, WEST }

    // Turn left by moving counterclockwise in the direction array
    public static Direction getTurnedLeftDirection(Direction currentDirection) {
        return Direction.values()[(currentDirection.ordinal() + 3) % 4];
    }

    // Turn right by moving clockwise in the direction array
    public static Direction getTurnedRightDirection(Direction currentDirection) {
        return Direction.values()[(currentDirection.ordinal() + 1) % 4];
    }

    // Get the movement offsets (x, y) for moving forward based on the current direction
    public static int[] getForwardStepOffsets(Direction currentDirection) {
        switch (currentDirection) {
            case NORTH: return new int[]{0, -1}; // Move up
            case EAST: return new int[]{1, 0};  // Move right
            case SOUTH: return new int[]{0, 1}; // Move down
            case WEST: return new int[]{-1, 0}; // Move left
            default: return new int[]{0, 0};   // No movement
        }
    }
}