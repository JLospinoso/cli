package net.lospi;

import com.thoughtworks.qdox.model.JavaClass;
import net.lospi.sphinx.JavaClassParser;
import net.lospi.sphinx.TestClassGenerator;
import net.lospi.sphinx.Utils;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Sphinx {
    private static final Logger logger = LoggerFactory.getLogger(Sphinx.class);

    public static void main(String[] args){
        Options options = new Options();
        options.addOption("f", true, "Path of Java source file");
        options.addOption("d", true, "Path of output destination");
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if(commandLine.hasOption("f")){
                String path = commandLine.getOptionValue("f");
                String source = Utils.fromFile(path);
                JavaClassParser javaClassParser = new JavaClassParser();
                List<JavaClass> parsedClass = javaClassParser.generate(source);
                TestClassGenerator generator = new TestClassGenerator();
                if(parsedClass.size() == 0){
                    throw new IllegalArgumentException("Could not parse " + path + " into a Java class.");
                }
                String testSource = generator.generate(parsedClass.get(0));
                if(commandLine.hasOption("d")){
                    String outputPath = commandLine.getOptionValue("d");
                    Utils.toFile(outputPath, testSource);
                } else {
                    System.out.println(testSource);
                }
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("sphx", options);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug(e.toString());
        }
    }
}
