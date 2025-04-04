package ca.mcmaster.se2aa4.mazerunner.commands;

import ca.mcmaster.se2aa4.mazerunner.MazeExplorer;

public class MoveForwardCommand implements MovementCommand {
    private final MazeExplorer explorer;

    public MoveForwardCommand(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public String execute() {
        explorer.moveForward();
        return "F";
    }
}