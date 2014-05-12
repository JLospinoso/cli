package net.lospi;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Sphinx {
    private static final Logger logger = LoggerFactory.getLogger(Sphinx.class);

    public static void main(String[] args){
        Options options = new Options();
        options.addOption("t", false, "Show the time");
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if(commandLine.hasOption("t")){
                logger.info("Current time: " + new Date(System.currentTimeMillis()));
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("sphx", options);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
            logger.debug(e.toString());
        }
    }
}
