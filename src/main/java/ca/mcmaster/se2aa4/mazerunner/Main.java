package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Set up CLI options
        Options options = new Options();
        options.addOption("i", "input", true, "Path to the maze input file");
        options.addOption("p", "path", true, "Path sequence to validate against the maze");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        String inputFilePath = null;
        String pathToValidate = null;

        try {
            cmd = parser.parse(options, args);
            
            if (cmd.hasOption("i")) {
                inputFilePath = cmd.getOptionValue("i");
            } else {
                throw new ParseException("Missing required -i option");
            }
            
            if (cmd.hasOption("p")) {
                pathToValidate = cmd.getOptionValue("p");
            }
        } catch (ParseException e) {
            logger.error("Error parsing command line: " + e.getMessage());
            logger.error("Usage: java -jar mazerunner.jar -i <inputfile> [-p <path>]");
            return;
        }

        try {
            logger.info("**** Reading the maze from file " + inputFilePath);
            Maze maze = new Maze();
            maze.loadMaze(inputFilePath);

            logger.info("**** Maze loaded successfully");
            Explorer explorer = new Explorer(maze);

            if (pathToValidate != null) {
                boolean isValid = explorer.validatePath(pathToValidate);
                logger.info("Path validation result: " + (isValid ? "Valid" : "Invalid"));
                System.out.println(isValid ? "VALID" : "INVALID");
            } else {
                explorer.solveRightHandMaze();
                logger.info("Factorized path: " + explorer.getFactorizedMovementPath());
                System.out.println(explorer.getFactorizedMovementPath());
            }

        } catch (IOException e) {
            logger.error("Error reading the maze file: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error computing the path: " + e.getMessage());
        }

        logger.info("** End of MazeRunner");
    }
}