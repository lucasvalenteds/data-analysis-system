package io.lucasvalenteds.das.engine;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

public class PathConfiguration {

    private static final Logger logger = LogManager.getLogger(PathConfiguration.class);

    private final Path inputFolder;
    private final Path outputFolder;
    private final String inputExtension;
    private final String outputExtension;

    public PathConfiguration(Pair<String, String> input, Pair<String, String> output) {
        this.inputFolder = Paths.get(input.getValue0());
        this.outputFolder = Paths.get(output.getValue0());
        this.inputExtension = input.getValue1();
        this.outputExtension = output.getValue1();
        logger.info("Expecting {} files at {}", inputExtension, inputFolder);
        logger.info("Exporting {} files to {}", outputExtension, outputFolder);
    }

    public Path inputFolder() {
        return inputFolder;
    }

    public Path outputFolder() {
        return outputFolder;
    }

    public Path resolveReportFilename(Path filename) {
        return outputFolder().resolve(filename.toString().replaceFirst(inputExtension, outputExtension));
    }

    public boolean hasInputExtension(Path path) {
        return path.toString().endsWith(inputExtension);
    }
}