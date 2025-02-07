package ca.mcmaster.se2aa4.mazerunner;

public enum MazeDirection {

    // Cardinal directions and movement associated
    NORTH(0, -1, "WEST", "EAST"),   // Upward movement
    EAST(1, 0, "NORTH", "SOUTH"),   // Right movement
    SOUTH(0, 1, "EAST", "WEST"),    // Downward movement
    WEST(-1, 0, "SOUTH", "NORTH");  // Left movement

    // Position 
    private final int xMovement; 
    private final int yMovement;

    // Turned position
    private final String leftRotation; 
    private final String rightRotation; 

    MazeDirection(int xMovement, int yMovement, String leftRotation, String rightRotation) {
        this.xMovement = xMovement;
        this.yMovement = yMovement;
        this.leftRotation = leftRotation;
        this.rightRotation = rightRotation;
    }

    public MazeDirection rotateLeft() {
        // Find the direction corresponding to a left turn
        return MazeDirection.valueOf(leftRotation);
    }

    public MazeDirection rotateRight() {
        // Find the direction corresponding to a right turn
        return MazeDirection.valueOf(rightRotation);
    }

    public int[] getOffset() {
        // Returns movement offset as {x, y}
        return new int[]{xMovement, yMovement};
    }
}