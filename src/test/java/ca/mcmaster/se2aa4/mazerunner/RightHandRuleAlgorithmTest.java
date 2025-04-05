package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.builders.MazeExplorerBuilder;
import ca.mcmaster.se2aa4.mazerunner.builders.MazeGridBuilder;
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
        // Load the maze data
        char[][] simpleGrid = mazeLoader.loadGrid("./examples/tiny.maz.txt");
        Position simpleEntryPos = mazeLoader.findEntryPosition(simpleGrid);
        Position simpleExitPos = mazeLoader.findExitPosition(simpleGrid);

        char[][] complexGrid = mazeLoader.loadGrid("./examples/small.maz.txt");
        Position complexEntryPos = mazeLoader.findEntryPosition(complexGrid);
        Position complexExitPos = mazeLoader.findExitPosition(complexGrid);

        // Use builders to create maze grids
        simpleMazeGrid = new MazeGridBuilder()
                .setGrid(simpleGrid)
                .setEntryPosition(simpleEntryPos)
                .setExitPosition(simpleExitPos)
                .build();

        complexMazeGrid = new MazeGridBuilder()
                .setGrid(complexGrid)
                .setEntryPosition(complexEntryPos)
                .setExitPosition(complexExitPos)
                .build();

        algorithm = new RightHandRuleAlgorithm();
    }

    @Test
    void testSolveSimpleMaze() {
        // Use builder to create explorer
        MazeExplorer explorer = new MazeExplorerBuilder()
                .setStartingPosition(simpleMazeGrid.getEntryPosition())
                .setStartingDirection(MazeDirection.SOUTH)
                .build();

        algorithm.solve(simpleMazeGrid, explorer);

        // Need to check if the explorer reached the exit position
        assertEquals(simpleMazeGrid.getExitPosition(), explorer.getPosition(),
                "Explorer should reach the exit position after algorithm execution");
    }

    @Test
    void testSolveComplexMaze() {
        MazeExplorer explorer = new MazeExplorerBuilder()
                .setStartingPosition(complexMazeGrid.getEntryPosition())
                .setStartingDirection(MazeDirection.SOUTH)
                .build();

        // Execute the algorithm to solve the maze
        algorithm.solve(complexMazeGrid, explorer);

        assertEquals(complexMazeGrid.getExitPosition(), explorer.getPosition(),
                "Explorer should reach the exit position after algorithm execution");
    }

    @Test
    void testSolutionValidity() {
        // Use builder to create explorer
        MazeExplorer explorer = new MazeExplorerBuilder()
                .setStartingPosition(complexMazeGrid.getEntryPosition())
                .setStartingDirection(MazeDirection.SOUTH)
                .build();

        List<String> solution = algorithm.solve(complexMazeGrid, explorer);

        for (String move : solution) {
            // Check for F, L, R, or RR
            assertTrue(move.equals("F") || move.equals("L") || move.equals("R") || move.equals("RR"),
                    "Move '" + move + "' should be one of F, L, R, or RR");
        }
    }

    @Test
    void testSolutionCompleteness() {
        // Use builder to create explorer
        MazeExplorer explorer = new MazeExplorerBuilder()
                .setStartingPosition(complexMazeGrid.getEntryPosition())
                .setStartingDirection(MazeDirection.SOUTH)
                .build();

        List<String> solution = algorithm.solve(complexMazeGrid, explorer);

        assertFalse(solution.isEmpty());
    }
}