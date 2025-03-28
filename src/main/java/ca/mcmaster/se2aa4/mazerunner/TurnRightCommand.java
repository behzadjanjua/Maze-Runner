package ca.mcmaster.se2aa4.mazerunner;

public class TurnRightCommand implements MovementCommand {
    @Override
    public void execute(MazeExplorer explorer) {
        explorer.turnRight();
    }

    @Override
    public String getCommandSymbol() {
        return "R";
    }
}