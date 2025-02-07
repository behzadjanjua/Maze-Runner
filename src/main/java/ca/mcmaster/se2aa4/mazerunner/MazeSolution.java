package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class MazeSolution {
    private final List<String> movementPath; // List of movements.

    public MazeSolution(List<String> movementPath) {
        this.movementPath = movementPath;
    }

    // Returns the canonical movement path.
    public String getCanonicalPath() {
        return PathFormatter.getCanonicalPath(movementPath);
    }

    // Returns the factorized movement path.
    public String getFactorizedPath() {
        return PathFormatter.getFactorizedPath(movementPath);
    }
}