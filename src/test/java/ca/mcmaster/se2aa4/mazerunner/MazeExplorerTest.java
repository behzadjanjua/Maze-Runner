package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MazeExplorerTest {
    private MazeExplorer explorer;
    private Position startPosition;

    @BeforeEach
    void setUp() {
        startPosition = new Position(0, 0);
        explorer = new MazeExplorer(startPosition, MazeDirection.EAST);
    }

    @Test
    void testInitialPosition() {
        assertEquals(startPosition, explorer.getPosition());
        assertEquals(MazeDirection.EAST, explorer.getDirection());
        assertTrue(explorer.getMovementPath().isEmpty());
    }

    @Test
    void testMoveForward() {
        explorer.moveForward();
        Position expectedPosition = new Position(0, 1);
        assertEquals(expectedPosition, explorer.getPosition());
        assertEquals(List.of("F"), explorer.getMovementPath());
    }

    @Test
    void testTurnLeft() {
        explorer.turnLeft();
        assertEquals(MazeDirection.NORTH, explorer.getDirection());
        assertEquals(List.of("L"), explorer.getMovementPath());
    }

    @Test
    void testTurnRight() {
        explorer.turnRight();
        assertEquals(MazeDirection.SOUTH, explorer.getDirection());
        assertEquals(List.of("R"), explorer.getMovementPath());
    }

    @Test
    void testTurnAround() {
        explorer.turnAround();
        assertEquals(MazeDirection.WEST, explorer.getDirection());
        assertEquals(List.of("R", "R"), explorer.getMovementPath());
    }

    @Test
    void testGetNextPosition() {
        Position nextPos = explorer.getNextPosition(MazeDirection.EAST);
        assertEquals(new Position(0, 1), nextPos);

        explorer.turnLeft();
        nextPos = explorer.getNextPosition(MazeDirection.NORTH);
        assertEquals(new Position(-1, 0), nextPos);
    }
}