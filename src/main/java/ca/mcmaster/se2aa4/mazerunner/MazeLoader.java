package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeLoader {

    public MazeGrid loadMaze(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder mazeData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            mazeData.append(line).append("\n");
        }
        reader.close();
    
        String[] lines = mazeData.toString().split("\n");
        
        // Replace blank lines (if they are between two lines containing '#' characters) with spaces -- for straight mazes
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().isEmpty()) {
                if (i > 0 && i < lines.length - 1) {
                    if (lines[i - 1].contains("#") && lines[i + 1].contains("#")) {
                        int length = lines[0].length();
                        lines[i] = generateSpaces(length);
                    }
                }
            }
        }
        
        // Determine the expected number of columns from the first line.
        int expectedColumns = lines[0].length();
        // Any row that is too short is padded w/ spaces.
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() < expectedColumns) {
                // The "%-Ns" format pads the string on the right with spaces until it has length N.
                lines[i] = String.format("%-" + expectedColumns + "s", lines[i]);
            }
        }
        
        int rows = lines.length;
        int cols = expectedColumns;
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].toCharArray();
        }
        
        Position entryPosition = null;
        Position exitPosition = null;
        
        // Looks for an entry position in the first column and an exit in the last column.
        for (int row = 0; row < rows; row++) {
            if (grid[row][0] == ' ') {
                entryPosition = new Position(row, 0);
            }
            if (grid[row][cols - 1] == ' ') {
                exitPosition = new Position(row, cols - 1);
            }
        }
        return new MazeGrid(grid, entryPosition, exitPosition);
    }

    private String generateSpaces(int length) {
        StringBuilder spaces = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            spaces.append(' ');
        }
        return spaces.toString();
    }

}