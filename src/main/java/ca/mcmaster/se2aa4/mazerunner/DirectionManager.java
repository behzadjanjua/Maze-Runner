package ca.mcmaster.se2aa4.mazerunner;

public class DirectionManager {
    public enum Direction { NORTH, EAST, SOUTH, WEST }

    public static Direction getTurnedLeftDirection(Direction currentDirection) {
        return Direction.values()[(currentDirection.ordinal() + 3) % 4];
    }

    public static Direction getTurnedRightDirection(Direction currentDirection) {
        return Direction.values()[(currentDirection.ordinal() + 1) % 4];
    }

    public static int[] getForwardStepOffsets(Direction currentDirection) {
        switch (currentDirection) {
            case NORTH: return new int[]{0, -1};
            case EAST:  return new int[]{1, 0};
            case SOUTH: return new int[]{0, 1};
            case WEST:  return new int[]{-1, 0};
            default:    return new int[]{0, 0};
        }
    }
}