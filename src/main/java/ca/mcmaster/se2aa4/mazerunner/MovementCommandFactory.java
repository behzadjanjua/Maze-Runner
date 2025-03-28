package ca.mcmaster.se2aa4.mazerunner;

public class MovementCommandFactory {
    public static MovementCommand createCommand(String symbol) {
        switch (symbol) {
            case "F":
                return new ForwardCommand();
            case "L":
                return new TurnLeftCommand();
            case "R":
                return new TurnRightCommand();
            default:
                throw new IllegalArgumentException("Invalid movement command symbol: " + symbol);
        }
    }
}