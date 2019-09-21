package io.lucasvalenteds.batch.v2;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class PathManager {

    private final Logger logger = LogManager.getLogger(PathManager.class);

    public PathManager() {
        logger.info("Input folder: " + directoryWithDatFiles());
        logger.info("Output folder: " + directoryWithDoneDatFiles());
    }

    Path directoryWithDatFiles() {
        return Paths.get("data", "in");
    }

    Path directoryWithDoneDatFiles() {
        return Paths.get("data", "out");
    }

    Path resolveReportFilename(Path filename) {
        return directoryWithDoneDatFiles()
            .resolve(filename.toString().replaceFirst(".dat", ".done.dat"));
    }

    boolean hasDatExtension(Path path) {
        return path.toString().endsWith(".dat");
    }
}