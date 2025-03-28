package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;

public class RightHandRuleAlgorithmTest {
    private MazeGrid simpleMazeGrid;
    private MazeGrid complexMazeGrid;
    private RightHandRuleAlgorithm algorithm;

    @BeforeEach
    void setUp() throws IOException {
        MazeLoader mazeLoader = new MazeLoader();
        simpleMazeGrid = mazeLoader.loadMaze("./examples/tiny.maz.txt");
        complexMazeGrid = mazeLoader.loadMaze("./examples/small.maz.txt");
        algorithm = new RightHandRuleAlgorithm();
    }

    @Test
    void testSolveSimpleMaze() {
        MazeExplorer explorer = new MazeExplorer(simpleMazeGrid.getEntryPosition(), MazeDirection.SOUTH);
        List<String> solution = algorithm.solve(simpleMazeGrid, explorer);

        MazeExplorer verifier = new MazeExplorer(simpleMazeGrid.getEntryPosition(), MazeDirection.SOUTH);
        for (String move : solution) {
            switch (move) {
                case "F": verifier.moveForward(); break;
                case "L": verifier.turnLeft(); break;
                case "R": verifier.turnRight(); break;
            }
        }
        assertEquals(simpleMazeGrid.getExitPosition(), verifier.getPosition());
    }

    @Test
    void testSolveComplexMaze() {
        MazeExplorer explorer = new MazeExplorer(complexMazeGrid.getEntryPosition(), MazeDirection.SOUTH);
        List<String> solution = algorithm.solve(complexMazeGrid, explorer);

        MazeExplorer verifier = new MazeExplorer(complexMazeGrid.getEntryPosition(), MazeDirection.SOUTH);
        for (String move : solution) {
            switch (move) {
                case "F": verifier.moveForward(); break;
                case "L": verifier.turnLeft(); break;
                case "R": verifier.turnRight(); break;
            }
        }
        assertEquals(complexMazeGrid.getExitPosition(), verifier.getPosition());
    }

    @Test
    void testSolutionValidity() {
        MazeExplorer explorer = new MazeExplorer(complexMazeGrid.getEntryPosition(), MazeDirection.SOUTH);
        List<String> solution = algorithm.solve(complexMazeGrid, explorer);

        for (String move : solution) {
            assertTrue(move.equals("F") || move.equals("L") || move.equals("R"));
        }
    }

    @Test
    void testSolutionCompleteness() {
        MazeExplorer explorer = new MazeExplorer(complexMazeGrid.getEntryPosition(), MazeDirection.SOUTH);
        List<String> solution = algorithm.solve(complexMazeGrid, explorer);

        assertFalse(solution.isEmpty());
    }
}