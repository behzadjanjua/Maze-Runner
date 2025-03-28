package ca.mcmaster.se2aa4.mazerunner;

public class TurnLeftCommand implements MovementCommand {
    @Override
    public void execute(MazeExplorer explorer) {
        explorer.turnLeft();
    }

    @Override
    public String getCommandSymbol() {
        return "L";
    }
}