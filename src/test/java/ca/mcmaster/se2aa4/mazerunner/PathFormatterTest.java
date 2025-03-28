package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class PathFormatterTest {

    @Test
    void testGetCanonicalPath() {
        List<String> moves = Arrays.asList("F", "F", "L", "F", "R");
        String canonicalPath = PathFormatter.getCanonicalPath(moves);
        assertEquals("FFLFR", canonicalPath);
    }

    @Test
    void testGetFactorizedPath() {
        List<String> moves = Arrays.asList("F", "F", "F", "L", "L", "F", "R");
        String factorizedPath = PathFormatter.getFactorizedPath(moves);
        assertEquals("3F2LFR", factorizedPath);
    }

    @Test
    void testGetFactorizedPathSingleMoves() {
        List<String> moves = Arrays.asList("F", "L", "R", "F");
        String factorizedPath = PathFormatter.getFactorizedPath(moves);
        assertEquals("FLRF", factorizedPath);
    }

    @Test
    void testExpandFactorizedPath() {
        String factorizedPath = "3F2LFR";
        String expandedPath = PathFormatter.expandFactorizedPath(factorizedPath);
        assertEquals("FFFLLFR", expandedPath);
    }

    @Test
    void testExpandFactorizedPathWithSpaces() {
        String factorizedPath = "3F 2L F R";
        String expandedPath = PathFormatter.expandFactorizedPath(factorizedPath);
        assertEquals("FFFLLFR", expandedPath);
    }

    @Test
    void testEmptyPath() {
        List<String> emptyMoves = Arrays.asList();
        String canonicalPath = PathFormatter.getCanonicalPath(emptyMoves);
        String factorizedPath = PathFormatter.getFactorizedPath(emptyMoves);
        assertEquals("", canonicalPath);
        assertEquals("", factorizedPath);
    }
}