package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class MazeGridTest {
    private MazeGrid mazeGrid;

    @BeforeEach
    void setUp() throws IOException {
        MazeLoader mazeLoader = new MazeLoader();
        mazeGrid = mazeLoader.loadMaze("./examples/tiny.maz.txt");
    }

    @Test
    void testIsWalkable() {
        assertTrue(mazeGrid.isWalkable(new Position(1, 1))); // Should be open
        assertFalse(mazeGrid.isWalkable(new Position(0, 0))); // Should be wall
    }

    @Test
    void testIsWalkableOutOfBounds() {
        assertFalse(mazeGrid.isWalkable(new Position(-1, 1))); // Above maze
        assertFalse(mazeGrid.isWalkable(new Position(100, 1))); // Below maze
        assertFalse(mazeGrid.isWalkable(new Position(1, -5))); // Left of maze
        assertFalse(mazeGrid.isWalkable(new Position(1, 100))); // Right of maze
    }

    @Test
    void testGetTile() {
        char tile = mazeGrid.getTile(new Position(1, 1));
        assertEquals(' ', tile); // Check for path tile
    }

    @Test
    void testGetEntryPosition() {
        Position entry = mazeGrid.getEntryPosition();
        assertNotNull(entry);
        assertTrue(mazeGrid.isWalkable(entry));
    }

    @Test
    void testGetExitPosition() {
        Position exit = mazeGrid.getExitPosition();
        assertNotNull(exit);
        assertTrue(mazeGrid.isWalkable(exit));
    }

    @Test
    void testGetDimensions() {
        assertTrue(mazeGrid.getRows() > 0);
        assertTrue(mazeGrid.getColumns() > 0);
    }
}
