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

    // Overrides the equals method to compare Positions by row and column
    @Override
    public boolean equals(Object obj) {
        if (this == obj)  // If comparing to itself, they are equal
            return true;
        if (obj == null || getClass() != obj.getClass())  // Check for null or different class
            return false;
        Position pos = (Position) obj;
        return row == pos.row && column == pos.column;  
    }

    // Provides a string representation of the Position in a (column, row) format
    @Override
    public String toString() {
        return "(" + column + ", " + row + ")";
    }
}