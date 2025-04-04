package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.builders.MazeExplorerBuilder;
import ca.mcmaster.se2aa4.mazerunner.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MazeExplorerTest {
    private MazeExplorer explorer;
    private Position startPosition;
    private CommandInvoker invoker;

    @BeforeEach
    void setUp() {
        startPosition = new Position(0, 0);
        explorer = new MazeExplorerBuilder()
                .setStartingPosition(startPosition)
                .setStartingDirection(MazeDirection.EAST)
                .build();
        // Initialize command invoker
        invoker = new CommandInvoker();
    }

    @Test
    void testInitialPosition() {
        assertEquals(startPosition, explorer.getPosition());
        assertEquals(MazeDirection.EAST, explorer.getDirection());
        assertTrue(explorer.getMovementPath().isEmpty());
    }

    @Test
    void testMoveForward() {
        // Use command pattern to move forward
        invoker.addCommand(new MoveForwardCommand(explorer));
        invoker.executeCommand();

        Position expectedPosition = new Position(0, 1);
        assertEquals(expectedPosition, explorer.getPosition());
        assertEquals(List.of("F"), explorer.getMovementPath());
    }

    @Test
    void testTurnLeft() {
        // Use command pattern to turn left
        invoker.addCommand(new TurnLeftCommand(explorer));
        invoker.executeCommand();

        assertEquals(MazeDirection.NORTH, explorer.getDirection());
        assertEquals(List.of("L"), explorer.getMovementPath());
    }

    @Test
    void testTurnRight() {
        // Use command pattern to turn right
        invoker.addCommand(new TurnRightCommand(explorer));
        invoker.executeCommand();

        assertEquals(MazeDirection.SOUTH, explorer.getDirection());
        assertEquals(List.of("R"), explorer.getMovementPath());
    }

    @Test
    void testTurnAround() {
        // Use command pattern to turn around
        invoker.addCommand(new TurnAroundCommand(explorer));
        invoker.executeCommand();

        assertEquals(MazeDirection.WEST, explorer.getDirection());
        assertEquals(List.of("R", "R"), explorer.getMovementPath());
    }

    @Test
    void testGetNextPosition() {
        Position nextPos = explorer.getNextPosition(MazeDirection.EAST);
        assertEquals(new Position(0, 1), nextPos);

        // Use command pattern to turn left
        invoker.addCommand(new TurnLeftCommand(explorer));
        invoker.executeCommand();

        nextPos = explorer.getNextPosition(MazeDirection.NORTH);
        assertEquals(new Position(-1, 0), nextPos);
    }
}