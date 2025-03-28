package ca.mcmaster.se2aa4.mazerunner;

public class ForwardCommand implements MovementCommand {
    @Override
    public void execute(MazeExplorer explorer) {
        explorer.moveForward();
    }

    @Override
    public String getCommandSymbol() {
        return "F";
    }
}