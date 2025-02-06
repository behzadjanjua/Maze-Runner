package ca.mcmaster.se2aa4.mazerunner;

public interface MazeSolvingAlgorithm {
    MazeSolution solve(MazeGrid maze, MazeExplorer explorer);
}