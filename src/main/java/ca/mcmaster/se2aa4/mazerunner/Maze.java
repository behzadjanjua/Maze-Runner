package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private char[][] grid; // 2D array representing the maze
    private int entryColumn, entryRow; // Coordinates of the entry point
    private int exitColumn, exitRow; // Coordinates of the exit point

    public void loadMaze(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder mazeDataBuilder = new StringBuilder();
        String line;

        // 1. Read the file line by line (including blank lines)
        while ((line = reader.readLine()) != null) {
            mazeDataBuilder.append(line).append("\n");
        }
        reader.close();

        // 2. Split into lines
        String[] lines = mazeDataBuilder.toString().split("\n");

        for (int i = 0; i < lines.length; i++) {
            // Check if this line is empty (or just whitespace)
            if (lines[i].trim().isEmpty()) {
                // Make sure it's not the first or last line
                if (i > 0 && i < lines.length - 1) {
                    // Check if previous and next lines contain '#'
                    if (lines[i - 1].contains("#") && lines[i + 1].contains("#")) {
                        // Replace current line with a line of spaces
                        // We'll base the length on lines[0].length()
                        int length = lines[0].length();
                        lines[i] = generateSpaces(length);
                    }
                }
            }
        }

        // 4. Builds the grid (assuming that all lines have at least the length of lines[0])
        grid = new char[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            grid[i] = lines[i].toCharArray();
        }

        // 5. Finds entry/exit
        findEntryExit();
    }

    // Locate the entry (west border) and exit (east border) in the maze
    private void findEntryExit() {
        for (int row = 0; row < grid.length; row++) {
            // Check leftmost column
            if (grid[row][0] == ' ') {
                entryColumn = 0;
                entryRow = row;
            }
            // Check rightmost column
            if (grid[row][grid[0].length - 1] == ' ') {
                exitColumn = grid[0].length - 1;
                exitRow = row;
            }
        }
    }

    // Helper method to generate a string of spaces of specified length
    private String generateSpaces(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public char getTile(int column, int row) {
        return grid[row][column];
    }

    public int getEntryColumn() {
        return entryColumn;
    }

    public int getEntryRow() {
        return entryRow;
    }

    public int getExitColumn() {
        return exitColumn;
    }

    public int getExitRow() {
        return exitRow;
    }
}