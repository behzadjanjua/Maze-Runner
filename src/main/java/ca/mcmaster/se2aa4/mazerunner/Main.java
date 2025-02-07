package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Define command-line options.
        Options options = new Options();
        options.addOption("i", "input", true, "Path to the maze input file");
        options.addOption("p", "path", true, "Path sequence to validate");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        String mazeFilePath = null;
        String pathToValidate = null;

        try {
            cmd = parser.parse(options, args);

            // Retrieve maze file path.
            if (cmd.hasOption("i")) {
                mazeFilePath = cmd.getOptionValue("i");
            } else {
                throw new ParseException("Missing required -i option");
            }

            // Retrieve path validation string if provided.
            if (cmd.hasOption("p")) {
                pathToValidate = cmd.getOptionValue("p");
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line: " + e.getMessage());
            System.out.println("Usage: java -jar mazerunner.jar -i <inputfile> [-p <path>]");
            return;
        }

        try {
            logger.info("Loading maze from file: " + mazeFilePath);
            MazeLoader loader = new MazeLoader();
            MazeGrid maze = loader.loadMaze(mazeFilePath);
            MazeExplorer explorer = new MazeExplorer(maze.getEntryPosition(), MazeDirection.EAST);

            if (pathToValidate != null) {
                // Validate the provided movement path.
                boolean valid = PathVerifier.validatePath(
                        pathToValidate, maze, maze.getEntryPosition(), maze.getExitPosition(), explorer.getDirection());

                if (valid) {
                    System.out.println("The inputted path is VALID");
                } else {
                    System.out.println("The inputted path is INVALID");
                }
            } else {
                // Solve the maze using the right-hand rule algorithm.
                MazeSolvingAlgorithm solver = new RightHandRuleAlgorithm();
                java.util.List<String> moves = solver.solve(maze, explorer);

                // Format and display the path. (Commented out canonical)
                // String canonicalPath = PathFormatter.getCanonicalPath(moves);

                String factorizedPath = PathFormatter.getFactorizedPath(moves);

                // logger.info("Canonical Path: " + canonicalPath);
                logger.info("Factorized Path: " + factorizedPath);

                System.out.println("Factorized Path: " + factorizedPath);
            }
        } catch (IOException e) {
            System.out.println(("Error reading maze file: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println("Error during maze processing: " + e.getMessage());
        }

        logger.info("** End of Maze Runner");
    }
}