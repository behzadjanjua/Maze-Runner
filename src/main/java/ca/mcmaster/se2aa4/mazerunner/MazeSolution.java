package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class MazeSolution {
    private final List<String> movementPath;

    public MazeSolution(List<String> movementPath) {
        this.movementPath = movementPath;
    }

    public String getCanonicalPath() {
        return PathFormatter.getCanonicalPath(movementPath);
    }

    public String getFactorizedPath() {
        return PathFormatter.getFactorizedPath(movementPath);
    }
}