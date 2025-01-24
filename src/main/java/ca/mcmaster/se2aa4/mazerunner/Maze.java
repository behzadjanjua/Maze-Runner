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

        // Read the file line by line and store it in a 2D array
        while ((line = reader.readLine()) != null) {
            mazeDataBuilder.append(line).append("\n");
        }
        reader.close();

        String[] lines = mazeDataBuilder.toString().split("\n");
        grid = new char[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            grid[i] = lines[i].toCharArray();
        }
        findEntryExit();
    }

    // Locate the entry (west border) and exit (east border) points in the maze
    private void findEntryExit() {
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][0] == ' ') {
                entryColumn = 0;
                entryRow = row;
            }
            if (grid[row][grid[0].length - 1] == ' ') {
                exitColumn = grid[0].length - 1;
                exitRow = row;
            }
        }
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