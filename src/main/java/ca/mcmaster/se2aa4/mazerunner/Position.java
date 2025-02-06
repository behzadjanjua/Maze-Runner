package ca.mcmaster.se2aa4.mazerunner;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isSamePosition(Position other) {
        if (other == null) {
            return false;
        }
        if (this.row != other.getRow()) {
            return false;
        }
        if (this.column != other.getColumn()) {
            return false;
        }
        return true;
    }

    // Used for comparing if explorer reaches exit position
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true; // Check if same reference
        if (obj == null || getClass() != obj.getClass())
            return false; // Check type
        Position position = (Position) obj;
        return row == position.row && column == position.column;
    }

    public String toString() {
        return "(" + column + ", " + row + ")";
    }
}