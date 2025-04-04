package ca.mcmaster.se2aa4.mazerunner.commands;

import ca.mcmaster.se2aa4.mazerunner.MazeExplorer;

public class TurnRightCommand implements MovementCommand {
    private final MazeExplorer explorer;

    public TurnRightCommand(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    @Override
    public String execute() {
        explorer.turnRight();
        return "R";
    }
}