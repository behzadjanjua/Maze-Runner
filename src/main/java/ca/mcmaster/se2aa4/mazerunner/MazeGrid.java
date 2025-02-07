package ca.mcmaster.se2aa4.mazerunner;

public class MazeGrid {
    private char[][] grid;
    private Position entryPosition;
    private Position exitPosition;
    
    public MazeGrid(char[][] grid, Position entryPosition, Position exitPosition) {
        this.grid = grid;
        this.entryPosition = entryPosition;
        this.exitPosition = exitPosition;
    }
    
    
    
    public boolean isWalkable(Position position) {
        // Check if the position is within the bounds of the grid.
        if (position.getRow() < 0 || position.getRow() >= grid.length || 
            position.getColumn() < 0 || position.getColumn() >= grid[0].length) {
            return false;
        }
        
        // A position is walkable if it contains a space (' ').
        return grid[position.getRow()][position.getColumn()] == ' ';
    }
    
    public char getTile(Position position) {
        // Returns the character at the given position in the maze.
        return grid[position.getRow()][position.getColumn()];
    }

    public Position getEntryPosition() {
        // Returns the starting position of the maze.
        return entryPosition;
    }
    
    public Position getExitPosition() {
        return exitPosition;
    }
    
    public int getRows() {
        // Returns the total number of rows in the maze.
        return grid.length;
    }
    
    public int getColumns() {
        // Returns the total number of columns in the maze.
        return grid[0].length;
    }
}