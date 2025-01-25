package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;
import java.util.List;

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

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        String inputFilePath = null;

        // Parse the command-line arguments
        try {
            cmd = parser.parse(options, args);

            // Check if the -i flag is provided, and get the input file path
            if (cmd.hasOption("i")) {
                inputFilePath = cmd.getOptionValue("i");
            } else {
                throw new ParseException("Missing required -i option");
            }
        } catch (ParseException e) {
            logger.error("Error parsing command line: " + e.getMessage());
            logger.error("Usage: java -jar mazerunner.jar -i <inputfile>");
            return;
        }

        // Load the maze and log entry/exit points
        try {
            logger.info("**** Reading the maze from file " + inputFilePath);
            Maze maze = new Maze();
            maze.loadMaze(inputFilePath);

            logger.info("**** Maze loaded successfully");
            logger.info("Entry point: (" + maze.getEntryColumn() + ", " + maze.getEntryRow() + ")");
            logger.info("Exit point: (" + maze.getExitColumn() + ", " + maze.getExitRow() + ")");

            logger.info("**** Computing path");

            // 1) Create Explorer for this maze
            Explorer explorer = new Explorer(maze);

            // 2) Solve the maze (straight path only)
            explorer.solveStraightMaze();

            // 3) Retrieve movement paths
            List<String> canonicalPath = explorer.getCanonicalMovementPath();
            String factorizedPath = explorer.getFactorizedMovementPath();

            // 4) Log results
            logger.info("Canonical path: " + String.join("", canonicalPath));
            logger.info("Factorized path: " + factorizedPath);

        } catch (IOException e) {
            // Specifically handle file-related I/O issues (e.g., file not found, read errors)
            logger.error("/!\\ An error occurred while reading the maze file: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other runtime errors (e.g., path computations, invalid data)
            logger.error("/!\\ An error occurred while computing the path: " + e.getMessage());
        }
        
        logger.info("** End of MazeRunner");
    }
}