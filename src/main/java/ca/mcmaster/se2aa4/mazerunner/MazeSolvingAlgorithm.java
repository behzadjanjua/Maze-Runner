package ca.mcmaster.se2aa4.mazerunner;

public interface MazeSolvingAlgorithm {
    // Solves the maze and returns the list of moves.
    java.util.List<String> solve(MazeGrid maze, MazeExplorer explorer);
}