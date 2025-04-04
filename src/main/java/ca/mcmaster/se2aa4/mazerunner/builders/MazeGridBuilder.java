package ca.mcmaster.se2aa4.mazerunner.builders;

import ca.mcmaster.se2aa4.mazerunner.MazeGrid;
import ca.mcmaster.se2aa4.mazerunner.Position;

public class MazeGridBuilder {
    private char[][] grid;
    private Position entryPosition;
    private Position exitPosition;

    public MazeGridBuilder() {
        // Default initialization
    }

    public MazeGridBuilder setGrid(char[][] grid) {
        this.grid = grid;
        return this;
    }

    public MazeGridBuilder setEntryPosition(Position entryPosition) {
        this.entryPosition = entryPosition;
        return this;
    }

    public MazeGridBuilder setEntryPosition(int row, int column) {
        this.entryPosition = new Position(row, column);
        return this;
    }

    public MazeGridBuilder setExitPosition(Position exitPosition) {
        this.exitPosition = exitPosition;
        return this;
    }

    public MazeGridBuilder setExitPosition(int row, int column) {
        this.exitPosition = new Position(row, column);
        return this;
    }

    public MazeGrid build() {
        // Validate that all required components are provided
        if (grid == null) {
            throw new IllegalStateException("Grid cannot be null");
        }
        if (entryPosition == null) {
            throw new IllegalStateException("Entry position cannot be null");
        }
        if (exitPosition == null) {
            throw new IllegalStateException("Exit position cannot be null");
        }

        return new MazeGrid(grid, entryPosition, exitPosition);
    }
}