package io.lucasvalenteds.das.engine;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathConfiguration {

    private final Logger logger = LogManager.getLogger(PathConfiguration.class);

    public PathConfiguration() {
        logger.info("Input folder: " + directoryWithDatFiles());
        logger.info("Output folder: " + directoryWithDoneDatFiles());
    }

    public Path directoryWithDatFiles() {
        return Paths.get("data", "in");
    }

    public Path directoryWithDoneDatFiles() {
        return Paths.get("data", "out");
    }

    public Path resolveReportFilename(Path filename) {
        return directoryWithDoneDatFiles()
            .resolve(filename.toString().replaceFirst(".dat", ".done.dat"));
    }

    public boolean hasDatExtension(Path path) {
        return path.toString().endsWith(".dat");
    }
}