package ca.mcmaster.se2aa4.mazerunner.commands;

import ca.mcmaster.se2aa4.mazerunner.MazeExplorer;

public class TurnAroundCommand implements MovementCommand {
    private final MazeExplorer explorer;

    public TurnAroundCommand(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public String execute() {
        explorer.turnAround();
        return "RR"; // Turn around is represented as two right turns
    }
}