package ca.mcmaster.se2aa4.mazerunner.builders;

import ca.mcmaster.se2aa4.mazerunner.MazeDirection;
import ca.mcmaster.se2aa4.mazerunner.MazeExplorer;
import ca.mcmaster.se2aa4.mazerunner.Position;

public class MazeExplorerBuilder {
    private Position startingPosition;
    private MazeDirection startingDirection = MazeDirection.EAST; // Default direction

    public MazeExplorerBuilder() {
        // Default initialization
    }

    public MazeExplorerBuilder setStartingPosition(Position startingPosition) {
        this.startingPosition = startingPosition;
        return this;
    }

    public MazeExplorerBuilder setStartingPosition(int row, int column) {
        this.startingPosition = new Position(row, column);
        return this;
    }

    public MazeExplorerBuilder setStartingDirection(MazeDirection startingDirection) {
        this.startingDirection = startingDirection;
        return this;
    }

    public MazeExplorer build() {
        // Validate that required components are provided
        if (startingPosition == null) {
            throw new IllegalStateException("Starting position cannot be null");
        }

        return new MazeExplorer(startingPosition, startingDirection);
    }
}