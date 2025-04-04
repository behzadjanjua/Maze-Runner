package ca.mcmaster.se2aa4.mazerunner.commands;

import ca.mcmaster.se2aa4.mazerunner.MazeExplorer;

public class TurnLeftCommand implements MovementCommand {
    private final MazeExplorer explorer;

    public TurnLeftCommand(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public String execute() {
        explorer.turnLeft();
        return "L";
    }
}