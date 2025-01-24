package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // CLI options
        Options options = new Options();
        options.addOption("i", "input", true, "Path to the maze input file");

        // command line parser
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        String inputFilePath = null;

        // Checking for -i flag, and then parsing
        try {
            cmd = parser.parse(options, args);

            // Step 3: Retrieve the value of the -i flag
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

        try {
            logger.info("**** Reading the maze from file " + args[0]);
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        logger.info("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        logger.info("PASS ");
                    }
                }
                logger.info(System.lineSeparator());
            }
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }

        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
