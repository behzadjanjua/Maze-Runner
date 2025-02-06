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

    public char getTile(Position position) {
        return grid[position.getRow()][position.getColumn()];
    }

    public boolean isWalkable(Position position) {
        if (position.getRow() < 0 || position.getRow() >= grid.length || position.getColumn() < 0 || position.getColumn() >= grid[0].length) {
            return false;
        }
        return grid[position.getRow()][position.getColumn()] == ' ';
    }

    public Position getEntryPosition() {
        return entryPosition;
    }

    public Position getExitPosition() {
        return exitPosition;
    }

    public int getRows() {
        return grid.length;
    }

    public int getColumns() {
        return grid[0].length;
    }
}