package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options commandLineOptions = new Options();
        commandLineOptions.addOption("i", "input", true, "Path to the maze input file");
        commandLineOptions.addOption("p", "path", true, "Path sequence to validate against the maze");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine parsedCommandLine;
        String mazeInputFilePath = null;
        String pathToValidate = null;

        try {
            parsedCommandLine = commandLineParser.parse(commandLineOptions, args);
            if (parsedCommandLine.hasOption("i")) {
                mazeInputFilePath = parsedCommandLine.getOptionValue("i");
            } else {
                throw new ParseException("Missing required -i option");
            }
            if (parsedCommandLine.hasOption("p")) {
                pathToValidate = parsedCommandLine.getOptionValue("p");
            }
        } catch (ParseException exception) {
            logger.error("Error parsing command line: " + exception.getMessage());
            logger.error("Usage: java -jar mazerunner.jar -i <inputfile> [-p <path>]");
            return;
        }

        try {
            logger.info("Reading maze from file: " + mazeInputFilePath);
            MazeLoader mazeLoader = new MazeLoader();
            MazeGrid maze = mazeLoader.loadMaze(mazeInputFilePath);
            MazeExplorer explorer = new MazeExplorer(maze.getEntryPosition(), DirectionManager.Direction.EAST);

            if (pathToValidate != null) {
                boolean isPathValid = PathVerifier.validatePath(pathToValidate, maze,
                        maze.getEntryPosition(), maze.getExitPosition(),
                        explorer.getDirection());
                System.out.println(isPathValid ? "VALID" : "INVALID");
            } else {
                MazeSolvingAlgorithm mazeSolver = new RightHandRuleAlgorithm();
                MazeSolution mazeSolution = mazeSolver.solve(maze, explorer);
                logger.info("Canonical Path: " + mazeSolution.getCanonicalPath());
                logger.info("Factorized Path: " + mazeSolution.getFactorizedPath());
                System.out.println("Canonical Path: " + mazeSolution.getCanonicalPath());
                System.out.println("Factorized Path: " + mazeSolution.getFactorizedPath());
            }
        } catch (IOException ioException) {
            logger.error("Error reading the maze file: " + ioException.getMessage());
        } catch (Exception exception) {
            logger.error("Error computing the path: " + exception.getMessage());
        }

        logger.info("** End of Maze Runner");
    }
}