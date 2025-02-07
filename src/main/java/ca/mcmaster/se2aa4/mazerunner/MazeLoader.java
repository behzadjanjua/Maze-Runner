package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeLoader {
    
    public MazeGrid loadMaze(String filePath) throws IOException {
        // Opening the file and read its contents line by line.
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder mazeData = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            mazeData.append(line).append("\n"); // Preserves line breaks for proper processing.
        }
        reader.close();
        
        // Convert the maze data into an array of lines.
        String[] lines = mazeData.toString().split("\n");

        // Handle cases where blank lines appear in the middle of the maze.
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().isEmpty() && i > 0 && i < lines.length - 1) {
                if (lines[i - 1].contains("#") && lines[i + 1].contains("#")) {
                    lines[i] = generateSpaces(lines[0].length()); // Replace blank lines with spaces.
                }
            }
        }
        
        // Ensure all rows have the same number of columns.
        int expectedColumns = lines[0].length();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() < expectedColumns) {
                lines[i] = String.format("%-" + expectedColumns + "s", lines[i]); // Pad shorter rows with spaces.
            }
        }
        
        // Convert the lines into a 2D character grid.
        int rows = lines.length;
        int cols = expectedColumns;
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].toCharArray();
        }
        
        // Identify the maze's entry and exit points.
        Position entryPosition = null;
        Position exitPosition = null;
        for (int row = 0; row < rows; row++) {
            if (grid[row][0] == ' ') { // Entry is any open space in the first column.
                entryPosition = new Position(row, 0);
            }
            if (grid[row][cols - 1] == ' ') { // Exit is any open space in the last column.
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